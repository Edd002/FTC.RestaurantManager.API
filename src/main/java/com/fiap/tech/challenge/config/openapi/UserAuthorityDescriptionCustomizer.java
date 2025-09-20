package com.fiap.tech.challenge.config.openapi;

import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.global.util.EnumUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import io.swagger.v3.oas.models.Operation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Strings;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;

import java.util.List;

@Component
public class UserAuthorityDescriptionCustomizer implements OperationCustomizer {

    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        var annotation = handlerMethod.getMethodAnnotation(PreAuthorize.class);
        if (ValidationUtil.isNotNull(annotation)) {
            List<Enum<?>> defaultUserPermissions = EnumUtil.getEnumsFromString(annotation.value(), DefaultUserTypeEnum.class);
            String permissions = ValidationUtil.isNotEmpty(defaultUserPermissions) ? StringUtils.join(defaultUserPermissions, ", ") : Strings.CS.remove(annotation.value(), "()");
            String summary = ValidationUtil.isNull(operation.getSummary()) ? StringUtils.EMPTY : (operation.getSummary() + "\n");
            String description = ValidationUtil.isNull(operation.getDescription()) ? StringUtils.EMPTY : (operation.getDescription() + "\n");
            operation.setSummary(summary + "Permissões necessárias: " + permissions);
            operation.setDescription("<b>" + description + "Permissões necessárias: " + permissions + "</b>");
        }
        return operation;
    }
}
