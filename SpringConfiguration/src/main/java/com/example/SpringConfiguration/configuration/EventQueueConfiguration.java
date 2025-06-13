package com.example.SpringConfiguration.configuration;

import com.example.SpringConfiguration.EventQueue;
import com.example.SpringConfiguration.EventQueueWorker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventQueueConfiguration {

    @Bean
    public EventQueueWorker eventQueueWorker(EventQueue eventQueue) {
        return new EventQueueWorker(eventQueue);
    }
}
