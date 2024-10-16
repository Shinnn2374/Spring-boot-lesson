package com.example.integration.controller;

import com.example.integration.model.EntityModel;
import com.example.integration.model.UpsertEntityRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/entity")
@Slf4j
public class EntityController
{
    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList()
    {
        List<EntityModel> entityModels = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            entityModels.add(EntityModel.createMockModel("Model" + (i + 1)));
        }
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name)
    {
        return ResponseEntity.ok(EntityModel.createMockModel(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.createMockModel(request.getName()));
    }
}
