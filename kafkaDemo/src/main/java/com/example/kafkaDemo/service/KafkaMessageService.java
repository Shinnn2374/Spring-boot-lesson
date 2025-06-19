package com.example.kafkaDemo.service;

import com.example.kafkaDemo.model.KafkaMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KafkaMessageService {

    private final List<KafkaMessage> messages = new ArrayList<>();

    public void add(KafkaMessage message) {
        messages.add(message);
    }

    public Optional<KafkaMessage> getById(Long id) {
        return messages.stream().filter(message -> message.getId().equals(id)).findFirst();
    }

}
