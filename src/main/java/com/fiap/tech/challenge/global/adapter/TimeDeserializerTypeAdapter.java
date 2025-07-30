package com.fiap.tech.challenge.global.adapter;

import com.fiap.tech.challenge.global.util.DateUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import lombok.extern.java.Log;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Date;

@Log
public class TimeDeserializerTypeAdapter implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return forceParseFormat(element.getAsString());
        } catch (ParseException e) {
            log.severe("Falha na conversão de campo data: " + e.getMessage());
            return null;
        }
    }

    private Date forceParseFormat(String date) throws ParseException {
        try {
            return DateUtil.configureDateFormat(DatePatternEnum.DATE_FORMAT_HH_mm.getValue()).parse(date);
        } catch (ParseException e) {
            return DateUtil.configureDateFormat(DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.getValue()).parse(date);
        }
    }
}