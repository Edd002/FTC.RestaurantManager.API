package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fiap.tech.challenge.global.util.DateUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeDeserializer extends JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        SimpleDateFormat dateFormat = DateUtil.configureDateFormat(DatePatternEnum.DATE_FORMAT_HH_mm.getValue());
        String informedStringDate = jsonparser.getText();

        if (informedStringDate != null && !informedStringDate.isEmpty()) {
            try {
                return dateFormat.parse(informedStringDate);
            } catch (ParseException e) {
                throw new JsonParseException(jsonparser, informedStringDate + " não é um horário válido.");
            }
        }

        return null;
    }
}