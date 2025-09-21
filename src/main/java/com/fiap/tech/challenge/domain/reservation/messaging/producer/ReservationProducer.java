package com.fiap.tech.challenge.domain.reservation.messaging.producer;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
public class ReservationProducer {

    private final String topic;
    private final KafkaTemplate<String, ReservationResponseDTO> kafkaTemplate;

    public ReservationProducer(@Value("${spring.kafka.topic.name.producer}") String topic, KafkaTemplate<String, ReservationResponseDTO> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ReservationResponseDTO reservationResponseDTO) {
        try {
            kafkaTemplate.send(topic, reservationResponseDTO);
            log.info("Mensagem de reserva enviada: " + reservationResponseDTO);
        } catch (Exception e) {
            log.severe("Erro: " + e.getCause());
            throw e;
        }
    }
}