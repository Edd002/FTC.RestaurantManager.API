package com.fiap.tech.challenge.config.openapi;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import io.swagger.v3.oas.models.Operation;
import org.apache.commons.lang3.StringUtils;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

@Component
public class UserAuthorityDescriptionCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        var annotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (ValidationUtil.isNotNull(annotation)) {
            String summary = ValidationUtil.isNull(operation.getSummary()) ? StringUtils.EMPTY : (operation.getSummary() + "\n");
            String description = ValidationUtil.isNull(operation.getDescription()) ? StringUtils.EMPTY : (operation.getDescription() + "\n");
            operation.setSummary(summary + "Permissões necessárias: " + String.join(" ou ", annotation.value()));
            operation.setDescription("<b>" + description + "Permissões necessárias: " + String.join(" ou ", annotation.value()) + "</b>");
        }
        return operation;
    }
}
