package com.example.rest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Schema
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "clients")
public class Client
{
    private Long id;
    private String name;
    private List<Order> orders = new ArrayList<>();

    public void addOrder(Order order)
    {
        orders.add(order);
    }
    public void removeOrder(Long orderId)
    {
        orders = orders.stream().filter(o -> o.getId().equals(orderId)).collect(Collectors.toList());
    }
}
