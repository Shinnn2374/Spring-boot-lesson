package com.example.integration_app.controller;

import com.example.integration_app.clients.OkHttpClientSender;
import com.example.integration_app.model.EntityModel;
import com.example.integration_app.model.UpsertEntityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/client/entity")
@RequiredArgsConstructor
public class EntityClientController
{
    private final OkHttpClientSender okHttpClientSender;

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList()
    {
        return ResponseEntity.ok(okHttpClientSender.getEntityList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> getEntityByName(@PathVariable String name)
    {
        return ResponseEntity.ok(okHttpClientSender.getEntityByName(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(okHttpClientSender.createEntity(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request)
    {
        return ResponseEntity.ok(okHttpClientSender.updatedEntity(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntityById(@PathVariable UUID id)
    {
        okHttpClientSender.deleteEntityById(id);
        return ResponseEntity.noContent().build();
    }
}
