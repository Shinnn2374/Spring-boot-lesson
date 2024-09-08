package com.example.REST_controllers.web.v1;

import com.example.REST_controllers.mapper.v1.OrderMapper;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.service.OrderService;
import com.example.REST_controllers.web.model.OrderListResponse;
import com.example.REST_controllers.web.model.OrderResponse;
import com.example.REST_controllers.web.model.UpsertOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderServiceImpl;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll()
    {
        return ResponseEntity.ok(orderMapper.orderListToOrderListResponse(orderServiceImpl.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(orderMapper.orderToResponse(orderServiceImpl.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody UpsertOrderRequest request)
    {
        Order newOrder = orderServiceImpl.save(orderMapper.requestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapper.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId, @RequestBody UpsertOrderRequest request)
    {
        Order updatedOrder = orderServiceImpl.update(orderMapper.requestToOrder(orderId, request));
        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        orderServiceImpl.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
