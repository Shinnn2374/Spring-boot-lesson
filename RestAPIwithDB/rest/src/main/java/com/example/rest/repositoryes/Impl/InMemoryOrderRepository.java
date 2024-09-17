package com.example.rest.repositoryes.Impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repositoryes.ClientRepository;
import com.example.rest.repositoryes.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryOrderRepository implements OrderRepository
{
    private ClientRepository clientRepository;
    private final Map<Long, Order> repository = new ConcurrentHashMap<>();
    private final AtomicLong currentId = new AtomicLong(1);

    @Autowired
    public void setRepository(ClientRepository clientRepository)
    {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Order> findAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Order save(Order order) {
        Long orderId = currentId.getAndIncrement();
        Long ClientId = order.getClient().getId();
        Client client = clientRepository.findById(ClientId).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Client {0} not found", ClientId)));
        order.setClient(client);
        order.setId(orderId);
        Instant now = Instant.now();
        order.setCreateAt(now);
        order.setUpdateAt(now);
        repository.put(orderId, order);
        return order;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void deleteById(Long orderId) {

    }

    @Override
    public void deleteByIdIn(List<Long> orderIds) {

    }
}
