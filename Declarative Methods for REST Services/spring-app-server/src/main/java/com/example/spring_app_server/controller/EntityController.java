package com.example.spring_app_server.controller;

import com.example.spring_app_server.model.EntityModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/api/v1/model")
@Slf4j
public class EntityController
{
    @GetMapping
    public ResponseEntity<List<EntityModel>> getAll()
    {
        List<EntityModel> models = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            models.add(EntityModel.builder()
                    .id(ThreadLocalRandom.current().nextLong())
                    .name("Entity Model " + i)
                    .age(20 + i)
                    .models(List.of(
                            EntityModel.builder()
                                    .id(ThreadLocalRandom.current().nextLong())
                                    .name("Sub model " + i)
                                    .age(10 + i)
                                    .build()
                    ))
                    .build());
        }
        return ResponseEntity.ok(models);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel> getById(@PathVariable Long id) {
        return ResponseEntity.ok(
                EntityModel.builder()
                        .id(ThreadLocalRandom.current().nextLong())
                        .name("Entity model " + id)
                        .age(20)
                        .build()
        );
    }
}
