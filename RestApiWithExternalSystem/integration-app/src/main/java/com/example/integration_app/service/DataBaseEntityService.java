package com.example.integration_app.service;

import com.example.integration_app.entity.DataBaseEntity;
import com.example.integration_app.repository.DataBaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataBaseEntityService
{
    private final DataBaseEntityRepository dataBaseEntityRepository;

    @Cacheable("databaseEntities")
    public List<DataBaseEntity> findAll()
    {
        return dataBaseEntityRepository.findAll();
    }

    public DataBaseEntity findById(UUID id)
    {
        return dataBaseEntityRepository.findById(id).orElseThrow();
    }

    @Cacheable("databaseEntityByName")
    public DataBaseEntity findByName(String name)
    {
        DataBaseEntity probe = new DataBaseEntity();
        probe.setName(name);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id","date");
        Example<DataBaseEntity> exampleOfProbe = Example.of(probe, matcher);
        return dataBaseEntityRepository.findOne(exampleOfProbe).orElseThrow();
    }

    @CacheEvict(value = "databaseEntities", allEntries = true)
    public DataBaseEntity create(DataBaseEntity entity)
    {
        DataBaseEntity forSave = new DataBaseEntity();
        forSave.setName(entity.getName());
        forSave.setDate(entity.getDate());
        return dataBaseEntityRepository.save(forSave);
    }

    @Caching(evict = {
            @CacheEvict(value = "databaseEntities",allEntries = true),
            @CacheEvict(value = "databaseEntityByName", allEntries = true)
    })
    public DataBaseEntity update(UUID id, DataBaseEntity entity)
    {
        DataBaseEntity forUpdate = findById(id);
        forUpdate.setName(entity.getName());
        forUpdate.setDate(entity.getDate());
        return dataBaseEntityRepository.save(forUpdate);
    }

    @Caching(evict = {
            @CacheEvict(value = "databaseEntities",allEntries = true),
            @CacheEvict(value = "databaseEntityByName", allEntries = true)
    })
    public void deleteById(UUID id)
    {
        dataBaseEntityRepository.deleteById(id);
    }
}
