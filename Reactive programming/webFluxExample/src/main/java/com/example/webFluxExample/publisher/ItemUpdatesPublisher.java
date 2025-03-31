package com.example.webFluxExample.publisher;

import com.example.webFluxExample.entity.Item;
import com.example.webFluxExample.model.ItemModel;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class ItemUpdatesPublisher {
    private final Sinks.Many<ItemModel> itemModelUpdatesSink;

    public ItemUpdatesPublisher() {
        this.itemModelUpdatesSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    public void publish(ItemModel model) {
        itemModelUpdatesSink.tryEmitNext(model);
    }

    public Sinks.Many<ItemModel> getUpdatesSink() {
        return itemModelUpdatesSink;
    }
}
