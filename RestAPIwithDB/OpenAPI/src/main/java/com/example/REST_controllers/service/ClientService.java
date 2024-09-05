package com.example.REST_controllers.service;

import com.example.REST_controllers.model.Client;

import java.util.List;

public interface ClientService
{
    List<Client> findAll();
    Client findById(Long id);
    Client save(Client client);
    Client update(Client client);
    void deleteById(Long id);
}
