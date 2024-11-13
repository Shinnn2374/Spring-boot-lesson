package com.example.spring_app_server.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
public class EntityModel
{
    private Long id;
    private String name;
    private Integer age;
    @Builder.Default
    private List<EntityModel> models = new ArrayList<>();
}
