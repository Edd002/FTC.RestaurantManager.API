package com.fiap.tech.challenge.global.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Pattern;

@UtilityClass
public class EmailValidatorUtil {

    public static final String EMAIL_REGEXP = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public static boolean isValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEXP);
        return pattern.matcher(email).matches();
    }
}