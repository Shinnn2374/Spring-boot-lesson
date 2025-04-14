package com.example.spring_jwt_auth_example.controller;

import com.example.spring_jwt_auth_example.client.JwtAuthClient;
import com.example.spring_jwt_auth_example.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/app/v1/client")
@RequiredArgsConstructor
public class AppController {

    private final JwtAuthClient jwtAuthClient;

//    @PostMapping
    @GetMapping
    public Mono<String> authRequest(@RequestBody AuthRequest authRequest) {
        return jwtAuthClient.getData();
//        return basicAuthClient.getData(authRequest);
    }
}
