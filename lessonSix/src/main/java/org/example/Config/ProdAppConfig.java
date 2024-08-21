package org.example.Config;

import org.example.ProdEnvPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application-prod.properties")
@Profile("prod")
public class ProdAppConfig
{

    @Bean
    public ProdEnvPrinter envPrinter()
    {
        return new ProdEnvPrinter();
    }
}
