package com.example.webFluxExample.controller;

import com.example.webFluxExample.entity.Item;
import com.example.webFluxExample.model.ItemModel;
import com.example.webFluxExample.publisher.ItemUpdatesPublisher;
import com.example.webFluxExample.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    private final ItemUpdatesPublisher publisher;

    @GetMapping
    public Flux<ItemModel> getAllItems() {
        return itemService.findAll().map(ItemModel::from);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ItemModel>> getItemById(@PathVariable String id) {
        return itemService.findById(id)
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{name}")
    public Mono<ResponseEntity<ItemModel>> getItemByName(@RequestParam String name) {
        return itemService.findByName(name)
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ItemModel>> createItem(@RequestBody ItemModel itemModel) {
        return itemService.save(Item.from(itemModel))
                .map(ItemModel::from)
                .doOnSuccess(publisher::publish)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ItemModel>> updateItem(@PathVariable String id, @RequestBody ItemModel itemModel) {
        return itemService.update(id, Item.from(itemModel))
                .map(ItemModel::from)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteItem(@PathVariable String id) {
        return itemService.deleteById(id).then(Mono.just(ResponseEntity.noContent().build()));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ItemModel>> getItemStream() {
        return publisher.getUpdatesSink()
                .asFlux()
                .map(item -> ServerSentEvent.builder(item).build());
    }
}
