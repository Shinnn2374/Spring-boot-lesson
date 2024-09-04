package com.example.REST_controllers.service.Impl;

import com.example.REST_controllers.exception.EntityNotFoundException;
import com.example.REST_controllers.exception.UpdateStateException;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.repository.OrderRepository;
import com.example.REST_controllers.service.OrderService;
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
    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id)
    {
        return orderRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Order with id {0} not found", id)));
    }

    @Override
    public Order save(Order order)
    {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order)
    {
        checkForUpdate(order.getId());
        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id)
    {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids)
    {
        orderRepository.deleteByIdIn(ids);
    }

    private void checkForUpdate(Long orderId)
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
