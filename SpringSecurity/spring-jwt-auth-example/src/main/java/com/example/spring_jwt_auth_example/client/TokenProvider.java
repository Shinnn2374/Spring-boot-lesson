package com.example.spring_jwt_auth_example.client;

import com.example.spring_jwt_auth_example.web.model.AuthRequest;
import com.example.spring_jwt_auth_example.web.model.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenProvider {

    @Value("${app.clientId}")
    private String clientId;

    @Value("${app.clientSecret}")
    private String clientSecret;

    private final WebClient defaultWebClient;

    private Mono<String> cachedToken;

    @SneakyThrows
    public Mono<String> getToken(boolean getFromCache) {
        if (!getFromCache || cachedToken == null) {
            log.info("Retrieve token and cache");
            cachedToken = retrieveToken()
                    .cache(Duration.ofMinutes(10))
                    .doOnError(throwable -> cachedToken = null);
        }
        return cachedToken;
    }

    @SneakyThrows
    private Mono<String> retrieveToken() {
        return defaultWebClient.post()
                .uri("/api/v1/auth/signin")
                .bodyValue(new AuthRequest(clientId,clientSecret))
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .map(TokenResponse::getToken);
    }
}
