package com.fiap.tech.challenge.global.util.string;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.apache.logging.log4j.util.Strings;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfStringValidator.class)
public @interface ValueOfString {

    String value() default Strings.EMPTY;

    String message() default Strings.EMPTY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}