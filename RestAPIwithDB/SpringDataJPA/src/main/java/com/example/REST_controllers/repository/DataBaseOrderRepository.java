package com.example.REST_controllers.repository;

import com.example.REST_controllers.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataBaseOrderRepository extends JpaRepository<Order, Long>
{
}
