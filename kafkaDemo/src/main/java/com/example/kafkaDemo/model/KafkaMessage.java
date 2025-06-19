package com.example.kafkaDemo.model;

import lombok.Data;

@Data
public class KafkaMessage {

    private Long id;

    private String message;
}
