package org.example.Config;

import org.example.EnvPrinter;
import org.example.TestEnvPrinter;
import org.springframework.context.annotation.*;

@Configuration
@PropertySources(
        value = {
                @PropertySource("classpath:application-test.properties")
        }
)
@Profile("test")
public class TestAppConfig
{
        @Bean
        public EnvPrinter envPrinter()
        {
                return new TestEnvPrinter();
        }
}
