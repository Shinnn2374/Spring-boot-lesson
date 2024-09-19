package com.example.rest.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration
{
    @Bean
    public OpenAPI openApiDescription()
    {
        Server localHost = new Server();
        localHost.setUrl("http://localhost:8080");
        localHost.setDescription("Local env");
        Server production = new Server();
        production.setUrl("http://some.prod.url");
        production.setDescription("Production env");

        Contact contact = new Contact();
        contact.setName("Mikhail Borissov");
        contact.setEmail("shinnn2374@mail.ru");
        contact.setUrl("https://github.com/shinnn2374");

        License license = new License().name("GNU AGPLv3")
                .url("https://chose.license.com.licenses.apgl.3.0");

        Info info = new Info()
                .title("Client orders api")
                .version("1.0")
                .contact(contact)
                .license(license)
                .description("API for client orders");
        return new OpenAPI().info(info).servers(List.of(localHost, production));
    }
}
