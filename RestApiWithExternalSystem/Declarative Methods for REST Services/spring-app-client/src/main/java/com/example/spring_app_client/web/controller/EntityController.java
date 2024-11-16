package com.example.spring_app_client.web.controller;

import com.example.spring_app_client.client.EntityModelClient;
import com.example.spring_app_client.web.model.EntityModel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/client/model")
@RequiredArgsConstructor
public class EntityController
{
    private final EntityModelClient client;
    @GetMapping
    public Flux<EntityModel> getAll() {
        return client.getModels();
    }
    @GetMapping("/{id}")
    public Mono<EntityModel> getById(@PathVariable Long id) {
        return client.getModel(id);
    }
    @PostMapping
    public Mono<EntityModel> createModel(@RequestBody EntityModel model) {
        return client.createModel(model);
    }
    @PutMapping("/{id}")
    public Mono<EntityModel> updateModel(@RequestBody EntityModel model, @PathVariable Long id) {
        return client.updateModel(model, id);
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteModel(@PathVariable Long id) {
        return client.deleteModel(id);
    }
    @GetMapping("/exception")
    public Mono<ResponseEntity<Void>> exceptionMethod() {
        return client.exceptionMethod();
    }
}
