package com.example.spring_jwt_auth_example.client;

import com.example.spring_jwt_auth_example.web.model.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class BasicAuthClient {

    private final WebClient defaultWebClient;

    public Mono<String> getData(AuthRequest request) {
        return defaultWebClient.get()
                .uri("/api/v1/user")
                .headers(h -> h.setBasicAuth(request.getUsername(), request.getPassword()))
                .retrieve()
                .bodyToMono(String.class);
    }
}
