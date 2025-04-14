package com.example.spring_jwt_auth_example.configuration;

import com.example.spring_jwt_auth_example.client.TokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:8080");
    }

    @Bean
    public WebClient jwtWebClient(TokenProvider tokenProvider) {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .filter(((request, next) -> annBearerToken(request,next,tokenProvider)))
                .filter(((request, next) -> retryUnauthorized(request,next,tokenProvider)))
                .build();
    }

    private Mono<ClientResponse> retryUnauthorized(ClientRequest request, ExchangeFunction next, TokenProvider tokenProvider) {
        return next.exchange(request)
                .flatMap(clientResponse -> {
                    if (clientResponse.statusCode().value() == HttpStatus.UNAUTHORIZED.value()) {
                        log.info("401 error. Get token and send request againt");
                        return tokenProvider.getToken(false).flatMap(
                          token -> {
                              var retryRequest = ClientRequest.from(request)
                                      .headers(h -> h.setBearerAuth(token))
                                      .build();
                              return next.exchange(retryRequest);
                          }
                        );
                    }
                    return next.exchange(request);
                });
    }

    private Mono<ClientResponse> annBearerToken(ClientRequest request, ExchangeFunction next, TokenProvider tokenProvider) {
        return tokenProvider.getToken(true).flatMap(token -> next.exchange(ClientRequest.from(request)
                .headers(h -> h.setBearerAuth(token))
                .build()));
    }
}
