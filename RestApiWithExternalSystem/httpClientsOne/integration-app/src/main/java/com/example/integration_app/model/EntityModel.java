package com.example.integration_app.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class EntityModel
{
    private UUID id;
    private String name;
    private Instant date;
}
