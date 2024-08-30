package com.example.firstLesson.repository;

import com.example.firstLesson.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class InMemoryTaskRepository implements TaskRepositpry
{
    private final List<Task> tasks = new ArrayList<>();


    @Override
    public List<Task> findAll() {
        log.debug("Call find all in InMemoryTaskRepository");
        return tasks;
    }

    @Override
    public Optional<Task> findById(Long id) {
        log.debug("Call find by id in InMemoryTaskRepository");
        return tasks.stream().filter(task -> task.getId().equals(id)).findFirst();
    }

    @Override
    public Task save(Task task) {
        log.debug("Call save in InMemoryTaskRepository");
        task.setId(System.currentTimeMillis());
        tasks.add(task);
        return task;
    }

    @Override
    public Task update(Task task) {
        log.debug("Call update in InMemoryTaskRepository. Task is {0}",task.getId());
        Task existedTask = findById(task.getId()).orElse(null);
        if (existedTask != null)
        {
            existedTask.setPriority(task.getPriority());
            existedTask.setDescription(task.getDescription());
            existedTask.setTitle(task.getTitle());
        }
        return task;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call delete by id - {0} in InMemoryTaskRepository",id);
        findById(id).ifPresent(task -> tasks.remove(task));
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        this.tasks.addAll(tasks);
    }
}
