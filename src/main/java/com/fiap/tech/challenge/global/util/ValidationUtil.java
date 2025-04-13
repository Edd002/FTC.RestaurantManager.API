package com.fiap.tech.challenge.global.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;

import java.net.http.HttpResponse;
import java.util.Collection;

@UtilityClass
public class ValidationUtil {

    public static boolean isNull(Object value) {
        return value == null;
    }

    public static boolean isNotNull(Object value) {
        return !isNull(value);
    }

    public static boolean isNotNullAndIsTrue(Boolean value) {
        return isNotNull(value) && value;
    }

    public static boolean isNotNullAndIsFalse(Boolean value) {
        return isNotNull(value) && !value;
    }

    public static boolean isEmpty(Collection<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNotEmpty(Collection<?> list) {
        return !isEmpty(list);
    }

    public static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotBlank(String value) {
        return !isBlank(value);
    }

    public static boolean isSuccess(HttpResponse<?> response) {
        return response.statusCode() == HttpStatus.OK.value();
    }

    public static boolean noSuccess(HttpResponse<?> response) {
        return response.statusCode() != HttpStatus.OK.value();
    }
}