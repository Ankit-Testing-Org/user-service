package com.app.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaUserProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaUserProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaUserProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserMessage(String message) {
        logger.info("Sending message to Kafka: {}", message);
        kafkaTemplate.send("user", message);
    }
}