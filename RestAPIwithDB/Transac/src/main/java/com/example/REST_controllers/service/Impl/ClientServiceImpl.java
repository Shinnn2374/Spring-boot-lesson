package com.example.REST_controllers.service.Impl;

import com.example.REST_controllers.exception.EntityNotFoundException;
import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.repository.ClientRepository;
import com.example.REST_controllers.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService
{
    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll()
    {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id)
    {
        return clientRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", id)));
    }

    @Override
    public Client save(Client client)
    {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client)
    {
        return clientRepository.update(client);
    }

    @Override
    public void deleteById(Long id)
    {
        clientRepository.deleteById(id);
    }

    @Override
    public Client saveWithOrders(Client client, List<Order> orders) {
        throw new NotImplementedException();
    }
}
