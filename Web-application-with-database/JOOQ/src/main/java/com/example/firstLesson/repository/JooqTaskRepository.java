package com.example.firstLesson.repository;

import com.example.firstLesson.Task;
import com.example.firstLesson.jooq.db.Tables;
import com.example.firstLesson.jooq.db.tables.records.TaskRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.jooq.Query;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
@Slf4j
public class JooqTaskRepository implements TaskRepositpry
{
    private final DSLContext dslContext;
    private final DSLContext dsl;

    @Override
    public List<Task> findAll() {
        log.debug("Calling findAll");
        return dslContext.selectFrom(Tables.TASK).
                fetchInto(Task.class);
    }

    @Override
    public Optional<Task> findById(Long id) {
        log.debug("Calling findById");
        return dslContext.selectFrom(Tables.TASK).
                where(Tables.TASK.ID.eq(id))
                .fetchOptional()
                .map(tableRecord -> tableRecord.into(Task.class));

    }

    @Override
    public Task save(Task task) {
        log.debug("Calling save");
        task.setId(System.currentTimeMillis());
        TaskRecord taskRecord = dslContext.newRecord(Tables.TASK,task);
        taskRecord.store();
        return taskRecord.into(Task.class);
    }

    @Override
    public Task update(Task task) {
        log.debug("Calling update");
        var maybeUpdateRecord = dslContext.update(Tables.TASK)
                .set(dslContext.newRecord(Tables.TASK,task))
                .where(Tables.TASK.ID.eq(task.getId()))
                .returning()
                .fetchOptional();
        return maybeUpdateRecord.map(taskRecord -> taskRecord.into(Task.class))
                .orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling deleteById");
        dslContext.deleteFrom(Tables.TASK).where(Tables.TASK.ID.eq(id)).execute();
    }

    @Override
    public void batchInsert(List<Task> tasks) {
        log.debug("Calling batchInsert");
        List<Query> insertQueryes = new ArrayList<>();

        for (Task task : tasks) {
            insertQueryes.add(dslContext.insertInto(
                    Tables.TASK,
                    Tables.TASK.ID,
                    Tables.TASK.TITLE,
                    Tables.TASK.DESCRIPTION,
                    Tables.TASK.PRIORITY
            ).values(
                    task.getId(),
                    task.getTitle(),
                    task.getDescription(),
                    task.getPriority()
                    )
            );
        }
        dslContext.batch(insertQueryes).execute();
    }
}
