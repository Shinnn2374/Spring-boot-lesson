package com.example.rest.rest.repository.impl;

import com.example.rest.rest.model.Client;
import com.example.rest.rest.model.Order;
import com.example.rest.rest.repository.ClientRepository;
import com.example.rest.rest.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryClientRepository implements ClientRepository {

    private OrderRepository orderRepository;

    private final Map<Long, Client> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

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
        Long id = currentId.getAndIncrement();
        client.setId(id);
        repository.put(id, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long id = client.getId();
        Client updatedClient = repository.get(id);
        if (updatedClient == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", id));
        }
        BeanUtils.copyNotNullProperties(client, updatedClient);
        updatedClient.setId(id);
        repository.put(id, updatedClient);
        return updatedClient;
    }

    @Override
    public void deleteById(Long id) {
        Client client = repository.get(id);
        if (client == null) {
            throw new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", id));
        }
        orderRepository.deleteByIdIn(client.getOrders().stream().map(Order::getId).collect(Collectors.toList()));
        repository.remove(id);
    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
