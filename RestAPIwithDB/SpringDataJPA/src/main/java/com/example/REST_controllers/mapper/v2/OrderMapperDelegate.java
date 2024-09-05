package com.example.REST_controllers.mapper.v2;

import com.example.REST_controllers.mapper.v1.OrderMapper;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.service.ClientService;
import com.example.REST_controllers.web.model.UpsertOrderRequest;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrderMapperDelegate implements OrderMapperV2
{
    @Autowired
    private ClientService dataBaseClientService;

    @Override
    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(dataBaseClientService.findById(request.getClientId()));
        return order;
    }

    @Override
    public Order requestToOrder(Long orderId, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(orderId);
        return order;
    }
}
