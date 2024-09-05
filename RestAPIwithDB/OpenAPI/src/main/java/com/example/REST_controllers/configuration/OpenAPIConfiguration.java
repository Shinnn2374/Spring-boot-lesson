package com.example.REST_controllers.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfiguration
{
    @Bean
    public OpenAPI openAPIDescription()
    {
        Server localHostServer = new Server();
        localHostServer.setUrl("http://localhost:8080");
        localHostServer.setDescription("Local env");

        Server prod = new Server();
        prod.setUrl("http://some.prod.url");
        prod.setDescription("Production env");

        Contact contact = new Contact();
        contact.setName("Ivan Ivanov");
        contact.setEmail("ivan@gmail.com");
        contact.setUrl("https://someUrl");

        License siteLicense = new License().name("GNU AGPLv3").url("https://choseLicense.com/Liceneses/agpl-3.0/");
        Info info = new Info()
                .title("Client Orders API")
                .version("1.0")
                .contact(contact)
                .description("API for client orders")
                .termsOfService("http://some.terms.url")
                .license(siteLicense);

        return new OpenAPI().info(info).servers(List.of(localHostServer, prod));
    }
}
