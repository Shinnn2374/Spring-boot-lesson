package com.example.LessonThree.core.impl;

import com.example.LessonThree.configuration.properties.BackGroundTaskProperties;
import com.example.LessonThree.core.BackGroundTaskExecutor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnExpression("'${background-executor.default-executor}'.equals('cron') and ${background-executor.default-executor.enable: true }")

public class CronBackGroundExecutor implements BackGroundTaskExecutor
{
    private final BackGroundTaskProperties backGroundTaskProperties;

    private final ConcurrentTaskScheduler concurrentTaskScheduler;

    private final TaskStopper taskStopper;

    @Override
    public void schedule(String id, Runnable task)
    {
        log.debug("TimeBackGroundTaskExecutor: Task with id - {} preparing for schedule ", id);
        var future = concurrentTaskScheduler.schedule(task, new CronTrigger(backGroundTaskProperties.getCron().getExpression()));
        taskStopper.registryTask(id, future);
    }
}
