package com.example.firstLesson.service;

import com.example.firstLesson.Task;
import com.example.firstLesson.repository.TaskRepositpry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService
{
    private final TaskRepositpry taskRepositpry;

    @Override
    public List<Task> findAll() {
        log.debug("Call findAll in TaskServiceImpl ");
        return taskRepositpry.findAll();
    }

    @Override
    public Task findById(Long id) {
        log.debug("Call findById in TaskServiceImpl ");
        return taskRepositpry.findById(id).orElse(null);
    }

    @Override
    public Task save(Task task) {
        log.debug("Call save in TaskServiceImpl ");
        return taskRepositpry.save(task);
    }

    @Override
    public Task update(Task task) {
        log.debug("Call update in TaskServiceImpl ");
        return taskRepositpry.update(task);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in TaskServiceImpl ");
        taskRepositpry.deleteById(id);
    }
}
