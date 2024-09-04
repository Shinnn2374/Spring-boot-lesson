package com.example.REST_controllers;

import com.example.REST_controllers.model.Client;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.web.model.ClientResponse;
import com.example.REST_controllers.web.model.OrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractTestController
{
    @Autowired
    protected  MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected Client createClient(Long id, Order order)
    {
        Client client = new Client(
                id,
                "Client" + id,
                new ArrayList<>()
        );
        if (order != null)
        {
            order.setClient(client);
            client.addOrder(order);
        }
        return client;
    }

    protected Order createOrder(Long id, Long cost, Client client)
    {
        return new Order(id,"Test product " + id, BigDecimal.valueOf(cost),client, Instant.now(), Instant.now());
    }

    protected ClientResponse createClientResponse(Long id, OrderResponse orderResponse)
    {
        ClientResponse response = new ClientResponse(
                id,
                "Client "+id,
                new ArrayList<>()
        );
        if (orderResponse != null)
        {
            response.getOrders().add(orderResponse);
        }
        return response;
    }
    protected OrderResponse createOrderResponse(Long id, Long cost)
    {
        return new OrderResponse(
                id,
                "Test product "+ id,
                BigDecimal.valueOf(cost)
        );
    }
}
