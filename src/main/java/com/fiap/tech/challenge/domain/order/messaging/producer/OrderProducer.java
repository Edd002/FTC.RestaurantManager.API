package com.fiap.tech.challenge.domain.order.messaging.producer;

import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
public class OrderProducer {

    private final String topic;
    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    public OrderProducer(@Value("${spring.kafka.topic.name.producer}") String topic, KafkaTemplate<String, OrderResponseDTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderResponseDTO orderResponseDTO) {
        try {
            kafkaTemplate.send(topic, orderResponseDTO);
            log.info("Mensagem enviada " + orderResponseDTO);
        } catch (Exception e) {
            log.severe("Erro: " + e.getCause());
            throw e;
        }
    }
}