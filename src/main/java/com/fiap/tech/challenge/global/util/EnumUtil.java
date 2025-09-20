package com.fiap.tech.challenge.global.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class EnumUtil {

    public static List<Enum<?>> getEnumsFromString(String text, Class<? extends Enum<?>> enumClass) {
        List<Enum<?>> enums = new ArrayList<>();
        for (Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (text.contains(enumValue.name())) {
                enums.add(enumValue);
            }
        }
        return enums;
    }
}