package com.example.firstLesson.service;

import com.example.firstLesson.Task;

import java.util.List;


public interface TaskService
{
    List<Task> findAll();
    Task findById(Long id);
    Task save(Task task);
    Task update(Task task);
    void deleteById(Long id);
    void batchInsert(List<Task> tasks);
}
