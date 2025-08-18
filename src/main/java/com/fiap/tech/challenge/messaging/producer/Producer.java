package com.fiap.tech.challenge.messaging.producer;

import com.fiap.tech.challenge.messaging.DTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
public class Producer {

    private final String topic;
    private final KafkaTemplate<String, DTO> kafkaTemplate;

    public Producer(@Value("${spring.kafka.topic.name.producer}") String topic, KafkaTemplate<String, DTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(DTO dto) {
        try {
            kafkaTemplate.send(topic, dto);
            log.info("Mensagem enviada " + dto);
        } catch (Exception e) {
            log.severe("Erro: " + e.getCause());
            throw e;
        }
    }
}