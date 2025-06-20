package com.example.webFlux.handler;

import com.example.webFlux.model.ItemModel;
import com.example.webFlux.model.SubItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class ItemHandler {

    public Mono<ServerResponse> getAllItems(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Flux.just(
                        new ItemModel(UUID.randomUUID().toString(), "Name 1", 10, Collections.emptyList()),
                        new ItemModel(UUID.randomUUID().toString(), "Name 2", 20, List.of(
                                new SubItemModel("subItem 1", BigDecimal.valueOf(1001)),
                                new SubItemModel("subItem 2", BigDecimal.valueOf(2002))
                        ))),ItemModel.class
                );
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(new ItemModel(request.pathVariable("id"), "Item name 1", 10, Collections.emptyList())), ItemModel.class);
    }

    public Mono<ServerResponse> createItem(ServerRequest request) {
        return request.bodyToMono(ItemModel.class)
                .flatMap(itemModel -> {
                    log.info("Creating new item {}", itemModel);
                    return Mono.just(itemModel);
                })
                .flatMap(item -> ServerResponse.created(URI.create(
                        "/api/v1/functions/item/" + item.getId()
                )).build());
    }

    public Mono<ServerResponse> errorRequest(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.error(new RuntimeException("Exception in error request")), String.class)
                .onErrorResume(ex -> {
                    log.error("Error in error request", ex);
                    return ServerResponse.badRequest().body(Mono.error(ex), String.class);
                });
    }
}
