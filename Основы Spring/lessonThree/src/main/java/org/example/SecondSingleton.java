package org.example;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Lazy
public class SecondSingleton
{
    private Instant createdTime;


    private final PrototypeComponent component;

    public SecondSingleton(PrototypeComponent component)
    {
        System.out.println("Second singleton created");
        createdTime = Instant.now();
        printCreatedTime();

        this.component = component;

    }

    public void printCreatedTime()
    {
        System.out.println("Second singleton created ad : " + createdTime);
    }
}
