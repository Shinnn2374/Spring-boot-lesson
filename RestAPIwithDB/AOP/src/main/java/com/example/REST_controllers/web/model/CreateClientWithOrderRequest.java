package com.example.REST_controllers.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientWithOrderRequest
{
    private String name;
    private List<OrderRequest> orders;

}
