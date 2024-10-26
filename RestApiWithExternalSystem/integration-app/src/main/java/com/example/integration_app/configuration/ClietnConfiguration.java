package com.example.integration_app.configuration;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClietnConfiguration
{
    public OkHttpClient okHttpClient()
    {
        return new OkHttpClient();
    }
}
