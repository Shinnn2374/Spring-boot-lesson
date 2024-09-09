package com.example.REST_controllers.service.Impl;

import com.example.REST_controllers.exception.EntityNotFoundException;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.repository.DataBaseClientRepository;
import com.example.REST_controllers.repository.DataBaseOrderRepository;
import com.example.REST_controllers.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataBaseClientService implements ClientService
{

    private final DataBaseClientRepository dataBaseClientRepository;
    private final DataBaseOrderRepository dataBaseOrderRepository;

    @Override
    public List<Client> findAll() {
        return dataBaseClientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return dataBaseClientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", id)));
    }

    @Override
    public Client save(Client client) {
        return dataBaseClientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return dataBaseClientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        dataBaseClientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Client saveWithOrders(Client client, List<Order> orders) {
        Client savedClient = dataBaseClientRepository.save(client);
        if (true) throw RuntimeException();
        for (Order order : orders) {
            order.setClient(savedClient);
            var savedOrder = dataBaseOrderRepository.save(order);
            savedClient.addOrder(savedOrder);
        }
        return savedClient;
    }
}
