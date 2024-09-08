package com.example.REST_controllers.repository;

import com.example.REST_controllers.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataBaseClientRepository extends JpaRepository<Client, Long>
{
}
