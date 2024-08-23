package org.example;

import org.springframework.beans.factory.annotation.Value;

public class ProdEnvPrinter implements EnvPrinter
{
    @Value(("${app.env}"))
    private String env;

    @Override
    public void printEnv()
    {
        System.out.println("Calling ProdEnvPrinter print env");
        System.out.println("ProdEnvPrinter env is " + env);

    }
}
