package com.example.integration_app.service;

import com.example.integration_app.entity.DataBaseEntity;
import com.example.integration_app.repository.DataBaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataBaseEntityService
{
    private final DataBaseEntityRepository repository;

    public List<DataBaseEntity> findAll()
    {
        return repository.findAll();
    }

    public DataBaseEntity findById(UUID id)
    {
        return repository.findById(id).orElseThrow();
    }

    public DataBaseEntity findByName(String name)
    {
        DataBaseEntity probe = new DataBaseEntity();
        probe.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id","date");
        Example<DataBaseEntity> example = Example.of(probe, matcher);
        return repository.findOne(example).orElseThrow();
    }

    public DataBaseEntity create(DataBaseEntity entity)
    {
        DataBaseEntity forSave = new DataBaseEntity();
        forSave.setName(entity.getName());
        forSave.setDate(entity.getDate());
        return repository.save(forSave);
    }

    public DataBaseEntity update(UUID id, DataBaseEntity entity)
    {
        DataBaseEntity forUpdate = findById(id);
        forUpdate.setName(entity.getName());
        forUpdate.setDate(entity.getDate());
        return repository.save(forUpdate);
    }

    public void deleteById(UUID id)
    {
        repository.deleteById(id);
    }
}
