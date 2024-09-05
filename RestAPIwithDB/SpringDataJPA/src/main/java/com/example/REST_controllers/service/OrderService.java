package com.example.REST_controllers.service;

import com.example.REST_controllers.exception.UpdateStateException;
import com.example.REST_controllers.model.Order;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public interface OrderService
{
    List<Order> findAll();
    Order findById(Long id);
    Order save(Order order);
    Order update(Order order);
    void deleteById(Long id);
    void deleteByIdIn(List<Long> ids);

    default void checkForUpdate(Long orderId)
    {
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrder.getUpdatedAt(), now);

        if (duration.getSeconds() > 5)
        {
            throw new UpdateStateException("Unable to update order");
        }
    }
}
