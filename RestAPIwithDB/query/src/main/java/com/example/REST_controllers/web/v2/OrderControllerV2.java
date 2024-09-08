package com.example.REST_controllers.web.v2;

import com.example.REST_controllers.mapper.v2.OrderMapperV2;
import com.example.REST_controllers.model.Order;
import com.example.REST_controllers.service.OrderService;
import com.example.REST_controllers.web.model.OrderFilter;
import com.example.REST_controllers.web.model.OrderListResponse;
import com.example.REST_controllers.web.model.OrderResponse;
import com.example.REST_controllers.web.model.UpsertOrderRequest;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/order")
@RequiredArgsConstructor
public class OrderControllerV2
{
    private final OrderService dataBaseOrderService;

    private final OrderMapperV2 orderMapperV2;


    @GetMapping("/filter")
    private ResponseEntity<OrderListResponse> filterby(@RequestParam String productName)
    {
        OrderFilter filter = new OrderFilter();
        filter.setProductName(productName);
        return ResponseEntity.ok(orderMapperV2.orderListToOrderListResponse(dataBaseOrderService.filterBy(
                filter
        )));
    }

    @GetMapping
    public ResponseEntity<OrderListResponse> findAll()
    {
        return ResponseEntity.ok(
                orderMapperV2.orderListToOrderListResponse(dataBaseOrderService.findAll())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(orderMapperV2.orderToResponse(dataBaseOrderService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody @Valid UpsertOrderRequest request)
    {
        Order newOrder = dataBaseOrderService.save(orderMapperV2.requestToOrder(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(orderMapperV2.orderToResponse(newOrder));
    }

    @PutMapping
    public ResponseEntity<OrderResponse> update(@PathVariable Long id, @RequestBody @Valid UpsertOrderRequest request)
    {
        Order updateOrder = dataBaseOrderService.update(orderMapperV2.requestToOrder(id, request));
        return ResponseEntity.ok(orderMapperV2.orderToResponse(updateOrder));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        dataBaseOrderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
