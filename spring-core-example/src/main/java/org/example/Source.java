package org.example;

public class Source
{
    @inject
    private Injectable injectable;

    public void call()
    {
        System.out.println("Soruce calling call");
        injectable.doWork();
    }
}
