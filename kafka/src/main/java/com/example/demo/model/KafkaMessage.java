package com.example.demo.model;

import lombok.Data;

@Data
public class KafkaMessage
{
    private Long id;
    private String message;
}
