package com.fiap.tech.challenge.global.adapter;

import com.fiap.tech.challenge.global.util.DateTimeUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.java.Log;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Log
public class MultiFormatDateDeserializerTypeAdapter implements JsonDeserializer<Date> {

    private static final List<DatePatternEnum> ALL_SUPPORTED_PATTERNS = Arrays.stream(DatePatternEnum.values()).toList();

    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        String dateString = element.getAsString();
        for (DatePatternEnum pattern : ALL_SUPPORTED_PATTERNS) {
            try {
                return DateTimeUtil.configureDateFormat(pattern.getValue()).parse(dateString);
            } catch (ParseException e) {
                log.severe("Falha na conversão de campo data: formato inesperado -> " + dateString);
            }
        }

        return null;
    }
}