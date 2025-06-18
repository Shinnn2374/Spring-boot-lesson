package com.example.rest.rest.mapper.v1;

import com.example.rest.rest.model.Order;
import com.example.rest.rest.services.ClientService;
import com.example.rest.rest.web.model.ClientResponse;
import com.example.rest.rest.web.model.OrderListResponse;
import com.example.rest.rest.web.model.OrderResponse;
import com.example.rest.rest.web.model.UpsertOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ClientService clientService;

    public Order requestToOrder(UpsertOrderRequest request) {
        Order order = new Order();
        order.setCost(request.getCost());
        order.setProduct(request.getProduct());
        order.setClient(clientService.findById(request.getClientId()));
        return order;
    }

    public Order requestToOrder(Long id, UpsertOrderRequest request) {
        Order order = requestToOrder(request);
        order.setId(id);
        return order;
    }

    public OrderResponse orderToResponse(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.setId(order.getId());
        orderResponse.setProduct(order.getProduct());
        orderResponse.setCost(order.getCost());
        return orderResponse;
    }

    public List<OrderResponse> orderListToResponseList(List<Order> orders) {
        return orders.stream().map(order -> orderToResponse(order)).collect(Collectors.toList());
    }

    public OrderListResponse orderListToOrderListResponse(List<Order> orders) {
        OrderListResponse orderListResponse = new OrderListResponse();
        orderListResponse.setOrders(orderListToResponseList(orders));
        return orderListResponse;
    }
}
