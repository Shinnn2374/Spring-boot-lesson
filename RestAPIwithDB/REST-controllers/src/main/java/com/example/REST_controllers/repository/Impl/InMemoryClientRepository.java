package com.example.REST_controllers.repository.Impl;

import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.repository.ClientRepository;
import com.example.REST_controllers.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryClientRepository implements ClientRepository
{
    private  OrderRepository orderRepository;

    private final Map<Long, Client> repository = new HashMap<>();

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
        Long clientId = currentId.getAndIncrement();
        client.setId(clientId);
        repository.put(clientId, client);
        return client;
    }

    @Override
    public Client update(Client client) {
        Long clientId = client.getId();
        Client currentClient = repository.get(clientId);
        if (currentClient == null) {
            throw new EntityNotFoundException("");
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
}
