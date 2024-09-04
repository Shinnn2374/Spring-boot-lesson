package com.example.REST_controllers.repository.Impl;

import com.example.REST_controllers.exception.EntityNotFoundException;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.repository.ClientRepository;
import com.example.REST_controllers.repository.OrderRepository;
import com.example.REST_controllers.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryClientRepository implements ClientRepository
{
    private  OrderRepository orderRepository;

    private final Map<Long, Client> repository = new HashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    @Override
    public List<Client> findAll()
    {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id)
    {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client)
    {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client)
    {
        Long clientId = client.getId();
        Client currentClient = repository.get(clientId);
        if (currentClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client {0} not found", clientId));
        }
        BeanUtils.copyNonNullProperties(client,currentClient);
        currentClient.setId(clientId);
        repository.put(clientId, currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long id)
    {
        Client client = repository.get(id);
        if (client == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client {0} not found", id));
        }
        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order:: getId).toList());
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
