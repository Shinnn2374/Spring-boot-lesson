package com.example.SpringConfiguration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor
public class EventQueueWorker {

    private final EventQueue eventQueue;

    @EventListener(ApplicationReadyEvent.class)
    public void startEventQueue() {
        startEventProducer();
        startEventConsumer();
    }

    private void startEventConsumer() {
        Thread eventConsumerThread = new Thread(() -> {
            while (true) {
                Event event = eventQueue.dequeue();
                System.out.println(event);
            }
        });
        eventConsumerThread.start();
    }

    private void startEventProducer() {
        Thread eventProducer = new Thread(() -> {
            while (true) {
                UUID id = UUID.randomUUID();
                Event event =  Event.builder()
                        .id(id)
                        .payload("payload for event - " + id)
                        .build();

                eventQueue.enqueue(event);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        eventProducer.start();
    }
}
