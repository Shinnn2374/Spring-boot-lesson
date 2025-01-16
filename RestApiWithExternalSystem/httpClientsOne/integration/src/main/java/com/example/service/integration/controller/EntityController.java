package com.example.service.integration.controller;

import com.example.service.integration.model.EntityModel;
import com.example.service.integration.model.UpsertEntityRequest;
import lombok.extern.slf4j.Slf4j;
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
    public ResponseEntity<List<EntityModel>> entitylist()
    {
        List<EntityModel> entityModels = new ArrayList<>();
        for (int i = 0; i < 10; i++)
        {
            entityModels.add(EntityModel.createMockModel("Entity" + (i+1)));
        }
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> entityByName(@PathVariable String name)
    {
        return ResponseEntity.ok(EntityModel.createMockModel(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createModel(@RequestBody UpsertEntityRequest request)
    {
        
    }
}
