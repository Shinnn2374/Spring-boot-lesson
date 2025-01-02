package com.example.service.integration_app.controller;

import com.example.service.integration_app.clients.OkHttpClientSender;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/client/entity")
@RequiredArgsConstructor
public class EntityClientController
{
    private final OkHttpClientSender client;

    
}
