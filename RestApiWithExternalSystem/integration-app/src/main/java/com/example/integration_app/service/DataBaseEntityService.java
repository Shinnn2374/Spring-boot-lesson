package com.example.integration_app.service;

import com.example.integration_app.entity.DataBaseEntity;
import com.example.integration_app.repository.DataBaseEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataBaseEntityService
{
    private final DataBaseEntityRepository dataBaseEntityRepository;

    public List<DataBaseEntity> findAll()
    {
        return dataBaseEntityRepository.findAll();
    }

    public DataBaseEntity findById(UUID id)
    {
        return dataBaseEntityRepository.findById(id).orElseThrow();
    }

    public DataBaseEntity findByName(String name)
    {
        return 
    }
}
