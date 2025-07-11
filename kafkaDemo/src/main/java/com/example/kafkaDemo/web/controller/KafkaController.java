package com.example.kafkaDemo.web.controller;

import com.example.kafkaDemo.model.KafkaMessage;
import com.example.kafkaDemo.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/kafka")
@RequiredArgsConstructor
public class KafkaController {

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    private final KafkaMessageService kafkaMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody KafkaMessage message) {
        kafkaTemplate.send(topicName, message);
        return ResponseEntity.ok("Message sent to kafka");
    }

    @GetMapping("/{id}")
    public ResponseEntity<KafkaMessage> getById(@PathVariable Long id) {
        return ResponseEntity.ok(kafkaMessageService.getById(id).orElseThrow());
    }
}
