package com.example.integration_app.controller;

import com.example.integration_app.client.OkHttpClientSender;
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
    private final OkHttpClientSender client;

    @GetMapping
    public ResponseEntity<List<EntityModel>> entityList()
    {
        return ResponseEntity.ok(client.getEntityList());
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> getEntityByName(@PathVariable String name)
    {
        return ResponseEntity.ok(client.getEntityByName(name));
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(client.createEntity(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request)
    {
        return ResponseEntity.ok(client.updateEntity(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntityById(@PathVariable UUID id)
    {
        client.deleteEntityById(id);
        return ResponseEntity.noContent().build();
    }

}
