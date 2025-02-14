package com.example.demo.listener;

import com.example.demo.model.KafkaMessage;
import com.example.demo.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaMessageListener
{
    private final KafkaMessageService kafkaMessageService;

    @KafkaListener(topics = "${app.kafka.kafkaMessageTopic}",
    groupId = "${app.kafka.kafkaMessageGroupId}",
    containerFactory = "kafkaMessageConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload KafkaMessage message, @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) Long key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC, required = false) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP, required = false) Long timestamp)
    {
        log.info("Received message: {}", message);
        log.info("Key: {}", key);
        log.info("Partition: {}", partition);
        log.info("Topic: {}", topic);
        log.info("Timestamp: {}", timestamp);
        kafkaMessageService.add(message);
    }
}
