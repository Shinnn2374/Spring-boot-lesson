package com.example.rest.rest.repository.impl;

import com.example.rest.rest.exceptions.EntityNotFoundException;
import com.example.rest.rest.model.Client;
import com.example.rest.rest.model.Order;
import com.example.rest.rest.repository.ClientRepository;
import com.example.rest.rest.repository.OrderRepository;
import com.example.rest.rest.utils.BeanUtils;
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
public class InMemoryOrderRepository implements OrderRepository {

    private ClientRepository clientRepository;

    private final Map<Long, Order> repository = new ConcurrentHashMap<>();

    private final AtomicLong currentId = new AtomicLong(1);

    public void setClientRepository(ClientRepository clientRepository) {
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
        Long id = currentId.getAndIncrement();
        Long clientId = order.getClient().getId();
        Client client = clientRepository.findById(clientId).orElseThrow(() -> new EntityNotFoundException("Client not found"));
        order.setClient(client);
        order.setId(id);
        Instant createdAt = Instant.now();
        order.setCreatedAt(createdAt);
        order.setUpdatedAt(createdAt);
        repository.put(id, order);
        client.addOrder(order);
        clientRepository.save(client);
        return order;
    }

    @Override
    public Order update(Order order) {
        Long id = order.getId();
        Instant now = Instant.now();
        Order currentOrder = repository.get(id);

        if (currentOrder == null) {
            throw new EntityNotFoundException(MessageFormat.format("Order with id {0} not found", order.getId()));
        }
        BeanUtils.copyNonNullProperties(order,currentOrder);
        currentOrder.setUpdatedAt(now);
        currentOrder.setId(id);
        repository.put(id, currentOrder);
        return currentOrder;
    }

    @Override
    public void deleteById(Long id) {
        repository.remove(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        ids.forEach(id -> repository.remove(id));
    }
}
