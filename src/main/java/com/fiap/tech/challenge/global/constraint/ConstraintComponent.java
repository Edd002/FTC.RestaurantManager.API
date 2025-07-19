package com.fiap.tech.challenge.global.constraint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ConstraintComponent {

    public String extractTableNameFromConstraintName(String constraintName) {
        String constraintNameUpperCase = constraintName.toUpperCase();
        return StringUtils.substringBefore(constraintNameUpperCase.substring(constraintNameUpperCase.indexOf("T_")), "__");
    }
}