package com.example.webFluxExample.repository;

import com.example.webFluxExample.entity.Item;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ItemRepository  extends ReactiveMongoRepository<Item, String> {
    Mono<Item> findByName(String name);
}
