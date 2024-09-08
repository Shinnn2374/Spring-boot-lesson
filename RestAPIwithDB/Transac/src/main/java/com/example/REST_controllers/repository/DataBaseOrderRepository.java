package com.example.REST_controllers.repository;

import com.example.REST_controllers.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DataBaseOrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order>
{
    Page<Order> findAllByProduct(String product, Pageable pageable);

}
