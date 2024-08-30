package com.example.LessonThree.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.scheduling.support.CronExpression;

@ConfigurationProperties(prefix = "background-executor")
@Getter
@Setter
public class BackGroundTaskProperties
{
    private boolean enabled;
    private String executor;
    private int taskSize;

    @NestedConfigurationProperty
    private CronExpression cron;

    @NestedConfigurationProperty
    private TimeExecutorProperties time;
}
