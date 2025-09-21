package com.fiap.tech.challenge.domain.reservation.messaging.producer;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.NetworkUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
public class ReservationProducer {

    private final String bootStrapAddress;
    private final String reservationTopic;
    private final KafkaTemplate<String, ReservationResponseDTO> kafkaTemplate;

    @Autowired
    public ReservationProducer(@Value(value = "${spring.kafka.bootstrap-servers}") String bootStrapAddress, @Value("${spring.kafka.topic.name.reservation-topic}") String reservationTopic, KafkaTemplate<String, ReservationResponseDTO> kafkaTemplate) {
        this.bootStrapAddress = bootStrapAddress;
        this.reservationTopic = reservationTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(ReservationResponseDTO reservationResponseDTO) {
        try {
            if (NetworkUtil.healthCheck(bootStrapAddress)) {
                kafkaTemplate.send(reservationTopic, reservationResponseDTO);
                log.info("Mensagem de reserva enviada: " + JsonUtil.toJson(reservationResponseDTO));
            } else {
                log.warning("Mensagem de reserva não enviada. Verifique se o serviço de mensageria Kafka está ativo.");
            }
        } catch (Exception exception) {
            log.severe("Erro: " + exception.getCause());
            throw exception;
        }
    }
}