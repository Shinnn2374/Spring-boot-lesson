package org.example;



public class SimpleBean
{
    public SimpleBean()
    {
        System.out.println("SimpleBean created");
    }


    public void afterInit()
    {
        System.out.println("SimpleBean calling afterInit");
    }

    public void beforeDestroy()
    {
        System.out.println("SimpleBean calling beforeDestroy");
    }
}
