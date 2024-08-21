package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class PersonInfo
{
    @Value("${app.person.name}")
    private String name;

    @Value("${app.person.surname}")
    private String surname;

    @Value("${app.person.age}")
    private int age;

    public void printInfo()
    {
        System.out.println(MessageFormat.format("Person info: {0} {1} , age: {2}", name, surname, age));
    }
}
