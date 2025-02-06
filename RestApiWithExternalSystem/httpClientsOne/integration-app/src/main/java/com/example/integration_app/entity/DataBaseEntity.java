package com.example.integration_app.entity;

import com.example.integration_app.model.EntityModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DataBaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Instant date;

    public static DataBaseEntity from(EntityModel model)
    {
        return new DataBaseEntity(model.getId(), model.getName(), model.getDate());
    }

}
