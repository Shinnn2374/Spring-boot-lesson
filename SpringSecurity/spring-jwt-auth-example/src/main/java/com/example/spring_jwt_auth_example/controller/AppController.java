package com.example.spring_jwt_auth_example.controller;

import com.example.spring_jwt_auth_example.client.BasicAuthClient;
import com.example.spring_jwt_auth_example.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app/v1/client")
@RequiredArgsConstructor
public class AppController {

    private final BasicAuthClient basicAuthClient;

    @PostMapping
    public Mono<String> authRequest(@RequestBody AuthRequest authRequest) {
        return basicAuthClient.getData(authRequest);
    }
}
