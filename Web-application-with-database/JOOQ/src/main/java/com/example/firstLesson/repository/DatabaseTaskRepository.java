package com.example.firstLesson.repository;

import com.example.firstLesson.Task;
import com.example.firstLesson.repository.mapper.TaskRowMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
//@Primary
@RequiredArgsConstructor
@Slf4j
public class DatabaseTaskRepository implements TaskRepositpry
{
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Task> findAll() {
        log.debug("Calling DatabaseTaskRepository findAll");
        String sql = "SELECT * FROM task";
        return jdbcTemplate.query(sql,  new TaskRowMapper());
    }

    @Override
    public Optional<Task> findById(Long id) {
        log.debug("Calling DatabaseTaskRepository findById");
        String sql = "SELECT * FROM task WHERE id = ?";
        Task task = DataAccessUtils.singleResult(
                jdbcTemplate.query(
                        sql,
                        new ArgumentPreparedStatementSetter(new Object[] {id}),
                        new RowMapperResultSetExtractor<>(new TaskRowMapper(),1)
                )
        );
        return Optional.ofNullable(task);
    }

    @Override
    public Task save(Task task) {
        log.debug("Calling DatabaseTaskRepository save");
        task.setId(System.currentTimeMillis());
        String sql = "INSERT INTO task (id, priority, title, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, task.getId(), task.getPriority(), task.getTitle(), task.getDescription());
        return null;
    }

    @Override
    public Task update(Task task) {
        log.debug("Calling DatabaseTaskRepository update");
        Task existTask = findById(task.getId()).orElse(null);
        if (existTask != null) {
            String sql = "UPDATE task SET priority = ?, title = ?, description = ? WHERE id = ?";
            jdbcTemplate.update(sql, task.getPriority(), task.getTitle(), task.getDescription(), task.getId());
            return task;
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Calling DatabaseTaskRepository deleteById");
        String sql = "DELETE FROM task WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public void batchInsert(List<Task> tasks) {
        log.debug("Calling DatabaseTaskRepository batchInsert");

        String sql = "INSERT INTO task (id, priority, title, description) VALUES (?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Task task = tasks.get(i);
                ps.setString(1, task.getDescription());
                ps.setString(2, task.getTitle());
                ps.setInt(3, task.getPriority());
                ps.setLong(4, task.getId());
            }

            @Override
            public int getBatchSize() {
                return tasks.size();
            }
        });
    }
}
