package com.example.REST_controllers.mapper.v2;

import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.web.model.OrderListResponse;
import com.example.REST_controllers.web.model.OrderResponse;
import com.example.REST_controllers.web.model.UpsertOrderRequest;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@DecoratedWith(OrderMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapperV2
{
    Order requestToOrder(UpsertOrderRequest request);

    @Mapping(source = "orderId", target = "id")
    Order requestToOrder(Long orderId, UpsertOrderRequest request);

    OrderResponse orderToResponse(Order order);

    List<OrderResponse> orderListToResponseList(List<Order> orders);

    default OrderListResponse orderListToOrderListResponse(List<Order> orders)
    {
        OrderListResponse response = new OrderListResponse();
        response.setOrders(orderListToResponseList(orders));
        return response;
    }
}
