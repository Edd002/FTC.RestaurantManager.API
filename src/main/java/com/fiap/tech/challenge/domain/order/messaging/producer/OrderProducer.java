package com.fiap.tech.challenge.domain.order.messaging.producer;

import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.global.util.JsonUtil;
import com.fiap.tech.challenge.global.util.NetworkUtil;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Log
@Service
public class OrderProducer {

    private final String bootStrapAddress;
    private final String orderTopic;
    private final KafkaTemplate<String, OrderResponseDTO> kafkaTemplate;

    @Autowired
    public OrderProducer(@Value(value = "${spring.kafka.bootstrap-servers}") String bootStrapAddress, @Value("${spring.kafka.topic.name.order-topic}") String orderTopic, KafkaTemplate<String, OrderResponseDTO> kafkaTemplate) {
        this.bootStrapAddress = bootStrapAddress;
        this.orderTopic = orderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(OrderResponseDTO orderResponseDTO) {
        try {
            if (NetworkUtil.healthCheck(bootStrapAddress)) {
                kafkaTemplate.send(orderTopic, orderResponseDTO);
                log.info("Mensagem de pedido enviada: " + JsonUtil.toJson(orderResponseDTO));
            } else {
                log.warning("Mensagem de pedido não enviada. Verifique se o serviço de mensageria Kafka está ativo.");
            }
        } catch (Exception exception) {
            log.severe("Erro: " + exception.getCause());
            throw exception;
        }
    }
}