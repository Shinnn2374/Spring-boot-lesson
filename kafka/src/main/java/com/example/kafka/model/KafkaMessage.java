package com.example.kafka.model;

import lombok.Data;

@Data
public class KafkaMessage {

    private Long id;
    private String message;
}
