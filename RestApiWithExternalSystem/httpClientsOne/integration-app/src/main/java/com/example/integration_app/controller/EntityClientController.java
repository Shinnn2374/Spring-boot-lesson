package com.example.integration_app.controller;

import com.example.integration_app.clients.OkHttpClientSender;
import com.example.integration_app.clients.OpenFeignClient;
import com.example.integration_app.clients.RestTemplateClient;
import com.example.integration_app.clients.WebClientSender;
import com.example.integration_app.entity.DataBaseEntity;
import com.example.integration_app.model.EntityModel;
import com.example.integration_app.model.UpsertEntityRequest;
import com.example.integration_app.service.DataBaseEntityService;
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
    private final OpenFeignClient client;
    private final DataBaseEntityService service

    @GetMapping
    public ResponseEntity<List<EntityModel>> getEntityList()
    {
        return ResponseEntity.ok(
                service.findAll().stream().map(EntityModel :: from).toList()
        );
    }

    @GetMapping("/{name}")
    public ResponseEntity<EntityModel> getEntityByName(@PathVariable String name)
    {
        return ResponseEntity.ok(
                EntityModel.from(service.findByName(name))
        );
    }

    @PostMapping
    public ResponseEntity<EntityModel> createEntity(@RequestBody UpsertEntityRequest request)
    {
        var newEntity = client.createEntity(request);
        var saveEntity= service.create(DataBaseEntity.from(newEntity));
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.from(saveEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> updateEntity(@PathVariable UUID id, @RequestBody UpsertEntityRequest request)
    {
        var updateEntity = client.updateEntity(id, request);
        var saveEntity= service.create(DataBaseEntity.from(updateEntity));
        return ResponseEntity.ok(EntityModel.from(saveEntity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntityById(@PathVariable UUID id)
    {
        client.deleteEntityById(id);
        return ResponseEntity.noContent().build();
    }
}
