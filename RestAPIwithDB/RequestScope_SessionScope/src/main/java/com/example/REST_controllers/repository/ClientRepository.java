package com.example.REST_controllers.repository;

import com.example.REST_controllers.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientRepository
{
    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client);
    Client update(Client client);
    void deleteById(Long id);
}
