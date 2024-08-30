package com.example.LessonThree.configuration;

import com.example.LessonThree.configuration.properties.BackGroundTaskProperties;
import com.example.LessonThree.core.impl.TaskStopper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Configuration
@ConditionalOnProperty("background-executor.enabled")
@EnableConfigurationProperties(BackGroundTaskProperties.class)
@Slf4j
public class BackGroundConfiguration
{
    @Bean
    public ConcurrentTaskScheduler concurrentTaskScheduler()
    {
        return new ConcurrentTaskScheduler();
    }

    @Bean
    public TaskStopper taskStopper(BackGroundTaskProperties taskProperties)
    {
        return new TaskStopper(new LinkedHashMap<>(){
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, ScheduledFuture<?>> eldest)
            {
                if (size() > taskProperties.getTaskSize())
                {
                    eldest.getValue().cancel(true);
                    log.debug("Eldest task preparing for delete . Id is {}", eldest.getKey());
                    return true;
                }
                return false;
            }
        });
    }
}
