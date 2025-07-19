package com.fiap.tech.challenge.global.constraint;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class ConstraintComponent {

    public String extractCleanConstraintNameFromConstraintName(String constraintName) {
        String constraintNameUpperCase = constraintName.toUpperCase();
        return constraintNameUpperCase.substring(constraintNameUpperCase.indexOf("T_"));
    }

    public String extractCleanTableNameFromConstraintName(String constraintName) {
        return StringUtils.substringBefore(extractCleanConstraintNameFromConstraintName(constraintName), "__");
    }
}