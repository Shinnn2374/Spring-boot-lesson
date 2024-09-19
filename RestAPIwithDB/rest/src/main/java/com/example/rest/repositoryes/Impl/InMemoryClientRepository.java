package com.example.rest.repositoryes.Impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repositoryes.ClientRepository;
import com.example.rest.repositoryes.OrderRepository;
import com.example.rest.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
public class InMemoryClientRepository implements ClientRepository
{
    private OrderRepository orderRepository;
    private final Map<Long, Client> repository = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Autowired
    public void setRepository(OrderRepository OrderRepository)
    {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Client> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Client save(Client client) {
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientId = client.getId();
        Client currentClient = repository.get(clientId);
        if (currentClient == null)
        {
            throw new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", clientId));
        }
        BeanUtils.copyNonNullProperties(client,currentClient);
        currentClient.setId(clientId);
        repository.put(clientId, currentClient);
        return currentClient;
    }

    @Override
    public void deleteById(Long clientId) {
        Client client = repository.get(clientId);
        if (client == null)
        {
            throw new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", clientId));
        }
//        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(clientId);
    }
}
