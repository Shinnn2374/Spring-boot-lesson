package com.example.firstLesson.configuration;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JooqConfiguration
{
    @Bean
    public DSLContext dsl(DataSource dataSource)
    {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }
}
