package com.fiap.tech.challenge.config.kafka;

import com.fiap.tech.challenge.global.util.NetworkUtil;
import lombok.NonNull;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class KafkaHealthCheck implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata annotatedTypeMetadata) {
        return NetworkUtil.healthCheck("broker:29092");
    }
}
