package com.example.rest.web.v1;

import com.example.rest.mapper.v1.OrderMapper;
import com.example.rest.model.Order;
import com.example.rest.service.OrderService;
import com.example.rest.web.model.OrderListResponse;
import com.example.rest.web.model.OrderResponse;
import com.example.rest.web.model.UpsertOrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController
{
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll()
    {
        return ResponseEntity.ok(orderMapper.orderListToOrderListResponse(orderService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(orderMapper.orderToResponse(orderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody UpsertOrderRequest request)
    {
        Order newOrder = orderService.save(orderMapper.requestToOrder(request));
        return ResponseEntity.ok(orderMapper.orderToResponse(newOrder));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> update(@PathVariable("id") Long orderId, @RequestBody UpsertOrderRequest request)
    {
        Order updatedOrder = orderService.update(orderMapper.requestToOrder(orderId, request));
        return ResponseEntity.ok(orderMapper.orderToResponse(updatedOrder));
    }

    @DeleteMapping("/id")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        orderService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
