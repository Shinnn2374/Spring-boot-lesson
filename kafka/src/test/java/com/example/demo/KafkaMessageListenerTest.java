package com.example.demo;

import com.example.demo.model.KafkaMessage;
import com.example.demo.service.KafkaMessageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
public class KafkaMessageListenerTest
{
    @Container
    static final KafkaContainer KAFKA = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.3.3")
    );

    @DynamicPropertySource
    static void registryKafkaProperties(DynamicPropertyRegistry registry)
    {
        registry.add("spring.kafka.bootstrap-servers", KAFKA::getBootstrapServers);
    }

    @Autowired
    private KafkaMessageService kafkaMessageService;

    @Autowired
    private KafkaTemplate<String,KafkaMessage> kafkaTemplate;

    @Value("${app.kafka.kafkaMessageTopic}")
    private String topicName;


    @Test
    public void whenSendKafkaMessage_thenHandleMessageByListener()
    {
        KafkaMessage event = new KafkaMessage();
        event.setId(1L);
        event.setMessage("Message from kafka");
        String key = UUID.randomUUID().toString();

        kafkaTemplate.send(topicName, key, event);
        await()
                .pollInterval(Duration.ofSeconds(3))
                .atMost(10, TimeUnit.SECONDS)
                .untilAsserted(() -> {
                    Optional<KafkaMessage> mayBeKafkaMessage = kafkaMessageService.getById(1L);
                    assertThat(mayBeKafkaMessage.isPresent());
                    KafkaMessage kafkaMessage = mayBeKafkaMessage.get();
                    assertThat(kafkaMessage.getMessage()).isEqualTo("Message from kafka");
                    assertThat(kafkaMessage.getId()).isEqualTo(1L);
                });
    }
}
