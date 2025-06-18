package com.example.rest.rest.repository.impl;

import com.example.rest.rest.model.Order;
import com.example.rest.rest.repository.ClientRepository;
import com.example.rest.rest.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryOrderRepository implements OrderRepository {

    private ClientRepository clientRepository;

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    

    @Override
    public List<Order> findAll() {
        return List.of();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public Order update(Order order) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void deleteByIdIn(List<Long> ids) {

    }

}
