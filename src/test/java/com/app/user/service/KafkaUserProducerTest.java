package com.app.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

public class KafkaUserProducerTest {
    private KafkaUserProducer kafkaUserProducer;
    private KafkaTemplate<String, String> kafkaTemplate;

    @BeforeEach
    public void setUp() {
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        kafkaUserProducer = new KafkaUserProducer(kafkaTemplate);
    }

    @Test
    public void testSendUserMessage() {
        String message = "User created";
        kafkaUserProducer.sendUserMessage(message);
        verify(kafkaTemplate).send("user", message);
    }
}