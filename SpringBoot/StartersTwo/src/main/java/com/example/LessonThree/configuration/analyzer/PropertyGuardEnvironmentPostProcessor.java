package com.example.LessonThree.configuration.analyzer;

import com.example.LessonThree.Exception.BackGroundTaskPropertiesException;
import com.example.LessonThree.configuration.properties.BackGroundTaskProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class PropertyGuardEnvironmentPostProcessor implements EnvironmentPostProcessor
{


    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application)
    {
        String defaultExecutor = environment.getProperty("background-executor.default-executor");
        String cronExpression = environment.getProperty("background-executor.cron.expression");
        String timeValue = environment.getProperty("background-executor.time.in-seconds-time");
        String tasksSize = environment.getProperty("background-executor.tasksSize");
        boolean enabled = Boolean.parseBoolean(environment.getProperty("background-executor.enabled"));

        if (enabled == true)
        {
            check(defaultExecutor,cronExpression,timeValue,tasksSize);
        }

    }

    public void check(String defaultExecutor, String cronExpression, String timeValue, String tasksSize)
    {
        boolean isInvalidType = !StringUtils.hasText(defaultExecutor) || (!Objects.equals(defaultExecutor, "cron")
                && !Objects.equals(defaultExecutor, "time"));

        if (isInvalidType)
        {
            throw new BackGroundTaskPropertiesException("Property backgroundexecutor default executor must be cron or time");
        }
        if (Objects.equals(defaultExecutor, "cron") && StringUtils.hasText(cronExpression))
        {
            throw  new BackGroundTaskPropertiesException("Invalid cron expression for 'cron' type! ");
        }
        if (Objects.equals(defaultExecutor, "time") && StringUtils.hasText(cronExpression))
        {
            throw  new BackGroundTaskPropertiesException("Invalid time expression for 'time' type! ");
        }
        if (!StringUtils.hasText(tasksSize) || tasksSize.matches("-?\\d*") || Integer.parseInt(tasksSize) >= 0)
        {
            throw new BackGroundTaskPropertiesException("Invalid tasks size value ");
        }
    }
}
