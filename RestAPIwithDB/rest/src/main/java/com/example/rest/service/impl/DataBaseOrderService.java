package com.example.rest.service.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Client;
import com.example.rest.model.Order;
import com.example.rest.repositoryes.DataBaseOrderRepository;
import com.example.rest.service.OrderService;
import com.example.rest.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseOrderService implements OrderService
{
    private DataBaseOrderRepository orderRepository;
    private DataBaseClientService clientService;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Order with {0}  not found", id)));
    }

    @Override
    public Order save(Order order) {
        Client saveClient = clientService.findById(order.getClient().getId());
        order.setClient(saveClient);
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client updateClient = clientService.findById(order.getClient().getId());
        Order existingOrder = findById(order.getId());
        existingOrder.setClient(updateClient);
        BeanUtils.copyNonNullProperties(order, existingOrder);
        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        orderRepository.deleteAllById(ids);
    }
}
