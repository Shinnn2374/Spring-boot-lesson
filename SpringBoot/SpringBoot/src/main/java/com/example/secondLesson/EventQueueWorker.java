package com.example.secondLesson;

import com.example.secondLesson.event.EventHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import java.util.UUID;

@RequiredArgsConstructor
public class EventQueueWorker
{
    private final EventQueue eventQueue;

    private final ApplicationEventPublisher applicationEventPublisher;

    @EventListener(ApplicationReadyEvent.class)
    public void startEventQueue()
    {
        startEventProducer();
        startEventConsumer();
    }

    private void startEventConsumer()
    {
        Thread eventConsumerThread = new Thread(() -> {
            while (true){
                Event event = eventQueue.deque();
//                System.out.println(event);
                applicationEventPublisher.publishEvent(new EventHolder(this,event));
            }
        });
        eventConsumerThread.start();
    }

    private void startEventProducer()
    {
        Thread eventProducerThread= new Thread(() -> {
            while(true){
                try{
                UUID id = UUID.randomUUID();
                Event event = Event.builder()
                        .id(id)
                        .pilot("Paylod for event" + id)
                        .build();

                eventQueue.enque(event);
                Thread.sleep(3000);
            }catch (InterruptedException e){
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }
        });
        eventProducerThread.start();
    }
}
