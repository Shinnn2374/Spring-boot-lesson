package com.example.firstLesson.repository;

import com.example.firstLesson.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepositpry
{
    List<Task> findAll();
    Optional<Task> findById(Long id);
    Task save(Task task);
    Task update(Task task);
    void deleteById(Long id);

}
