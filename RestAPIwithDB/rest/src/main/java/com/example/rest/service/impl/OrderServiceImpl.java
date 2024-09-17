package com.example.rest.service.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.exception.UpdateStateException;
import com.example.rest.model.Order;
import com.example.rest.repositoryes.OrderRepository;
import com.example.rest.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Order with id {0} not found", id)));
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        return orderRepository.update(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteByIdIn(ids);
    }

    private void checkForUpdate(Long orderId)
    {
        Order currentOrder = findById(orderId);
        Instant now = Instant.now();
        Duration duration = Duration.between(currentOrder.getUpdateAt(), now);
        if (duration.toMinutes() > 5)
        {
            throw new UpdateStateException(MessageFormat.format("Order with id {0} has already been updated", orderId));
        }
    }
}
