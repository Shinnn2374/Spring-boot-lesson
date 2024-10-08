package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DefaultEnvPrinter implements EnvPrinter
{

    @Value("${app.env}")
    private String env;

    @Override
    public void printEnv()
    {
        System.out.println("Calling DefaultEnvPrinter print env");
        System.out.println("DefaultEnvPrinter env is " + env);
    }
}
