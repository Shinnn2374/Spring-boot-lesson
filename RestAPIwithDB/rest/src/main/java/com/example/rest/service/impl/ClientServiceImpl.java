package com.example.rest.service.impl;

import com.example.rest.exception.EntityNotFoundException;
import com.example.rest.model.Client;
import com.example.rest.repositoryes.ClientRepository;
import com.example.rest.service.ClientService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService
{
    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(MessageFormat.format("Client with id {0} not found", id)));
    }

    @Override
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }
}
