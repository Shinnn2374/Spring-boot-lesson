package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("org.example")
public class AppConfig
{
    @Bean(initMethod ="afterInit", destroyMethod = "beforeDestroy")
    public SimpleBean simpleBean()
    {
        return new SimpleBean();
    }
}
