package com.example.REST_controllers.service.Impl;

import com.example.REST_controllers.exception.EntityNotFoundException;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.repository.DataBaseOrderRepository;
import com.example.REST_controllers.repository.OrderRepository;
import com.example.REST_controllers.repository.OrderSpecification;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.service.OrderService;
import com.example.REST_controllers.utils.BeanUtils;
import com.example.REST_controllers.web.model.OrderFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseOrderService implements OrderService
{
    private final DataBaseOrderRepository dataBaseOrderRepository;
    @Qualifier("clientServiceImpl")
    private final ClientService databaseClientService;

    @Override
    public List<Order> filterBy(OrderFilter filter) {
        return dataBaseOrderRepository.findAll(OrderSpecification.withFilter(filter),
                PageRequest.of(filter.getPageNumber(),filter.getPageSize())).getContent();
    }

    @Override
    public List<Order> findAll() {
        return dataBaseOrderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return dataBaseOrderRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Order with id {0} not found", id)));
    }

    @Override
    public Order save(Order order) {
        Client client = databaseClientService.findById(order.getClient().getId());
        order.setClient(client);
        return dataBaseOrderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        checkForUpdate(order.getId());
        Client client = databaseClientService.findById(order.getClient().getId());
        Order existingOrder =findById(order.getId());
        BeanUtils.copyNonNullProperties(order, existingOrder);
        existingOrder.setClient(client);
        return dataBaseOrderRepository.save(existingOrder);
    }

    @Override
    public void deleteById(Long id) {
        dataBaseOrderRepository.deleteById(id);
    }

    @Override
    public void deleteByIdIn(List<Long> ids) {
        dataBaseOrderRepository.deleteAllById(ids);
    }
}
