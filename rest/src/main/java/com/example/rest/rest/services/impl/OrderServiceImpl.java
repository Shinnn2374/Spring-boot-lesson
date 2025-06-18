package com.example.rest.rest.services.impl;

import com.example.rest.rest.exceptions.EntityNotFoundException;
import com.example.rest.rest.exceptions.UpdateStateException;
import com.example.rest.rest.model.Order;
import com.example.rest.rest.repository.OrderRepository;
import com.example.rest.rest.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(MessageFormat.format("Order with id {0} not found", id))
        );
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkUpdate(order.getId());
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

    private void checkUpdate(Long orderId) {
        Order order = findById(orderId);
        Instant now = Instant.now();

        Duration duration = Duration.between(order.getUpdatedAt(), now);
        if (duration.getSeconds() > 5){
            throw new UpdateStateException(MessageFormat.format("Order with id {0} has been updated a long time ago", orderId));
        }
    }
}
