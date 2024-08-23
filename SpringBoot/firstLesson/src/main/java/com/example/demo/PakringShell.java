package com.example.demo;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@ShellComponent
public class PakringShell
{
    private final Map<Integer, Integer> slots = new HashMap<>();

    @ShellMethod(key = "i")
    public String init(@ShellOption(value = "b") int big, @ShellOption(value = "m") int medium, @ShellOption(value = "s") int small)
    {
        slots.put(1,big);
        slots.put(2,medium);
        slots.put(3,small);

        return MessageFormat.format("Slots: [big: {0}, medium: {1}, small: {2}]", big, medium, small);
    }

    @ShellMethod(key = "a")
    @ShellMethodAvailability("canAddCar")
    public String addCar(int carType)
    {
        int newValue = slots.get(carType) - 1;
        if (newValue >= 0){
            slots.put(carType, newValue);
            return "Car was parking";
        }
        return "not space for parking";
    }


    public Availability canAddCar()
    {
        int count = 0;
        for (Map.Entry<Integer, Integer> entry : slots.entrySet())
        {
            int v = entry.getValue();
            count += v;
        }
        return count == 0 ? Availability.unavailable("Parking is full") : Availability.available();
    }
}
