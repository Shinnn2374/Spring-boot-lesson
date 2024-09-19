package com.example.rest.repositoryes;

import com.example.rest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataBaseOrderRepository extends JpaRepository<Order, Long> {
}
