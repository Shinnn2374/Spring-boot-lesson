package com.example.rest.rest.services;

import com.example.rest.rest.model.Order;

import java.util.List;

public interface OrderService {

    List<Order> getOrders();

    Order findById(Long id);

    Order save(Order order);

    Order update(Order order);

    void deleteById(Long id);

    void deleteByIdIn(List<Long> ids);
}
