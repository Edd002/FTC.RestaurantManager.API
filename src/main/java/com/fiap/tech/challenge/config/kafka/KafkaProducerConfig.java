package com.fiap.tech.challenge.config.kafka;

import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationResponseDTO;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Conditional(KafkaHealthCheck.class)
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapAddress;

    @Value(value = "${spring.kafka.topic.name.order-topic}")
    private String orderTopic;

    @Value(value = "${spring.kafka.topic.name.reservation-topic}")
    private String reservationTopic;

    @Bean
    public NewTopic createOrderTopic() {
        return new NewTopic(orderTopic, 3, (short) 1);
    }

    @Bean
    public NewTopic createReservationTopic() {
        return new NewTopic(reservationTopic, 3, (short) 1);
    }

    @Bean
    public KafkaTemplate<String, OrderResponseDTO> orderKafkaTemplate() {
        return new KafkaTemplate<>(orderProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, ReservationResponseDTO> reservationKafkaTemplate() {
        return new KafkaTemplate<>(reservationProducerFactory());
    }

    @Bean
    public ProducerFactory<String, OrderResponseDTO> orderProducerFactory() {
        return new DefaultKafkaProducerFactory<>(configProperties());
    }

    @Bean
    public ProducerFactory<String, ReservationResponseDTO> reservationProducerFactory() {
        return new DefaultKafkaProducerFactory<>(configProperties());
    }

    private Map<String, Object> configProperties() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return properties;
    }
}