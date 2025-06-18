package com.example.rest.rest.services;

import com.example.rest.rest.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAllClients();

    Client findById(Long id);

    Client save(Client client);

    Client update(Client client);

    void deleteById(Long id);
}
