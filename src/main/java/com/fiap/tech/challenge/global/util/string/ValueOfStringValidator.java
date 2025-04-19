package com.fiap.tech.challenge.global.util.string;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.math.NumberUtils;

public class ValueOfStringValidator implements ConstraintValidator<ValueOfString, String> {

    private String value;

    @Override
    public void initialize(ValueOfString constraintAnnotation) {
        this.value = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return !NumberUtils.isParsable(this.value) && (!ValidationUtil.isNotNull(this.value) || (!this.value.equals(Boolean.TRUE.toString()) && !this.value.equals(Boolean.FALSE.toString())));
    }
}