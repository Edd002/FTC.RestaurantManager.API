package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fiap.tech.challenge.global.exception.DateRangeException;
import com.fiap.tech.challenge.global.util.DateTimeUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDeserializer extends JsonDeserializer<Date> {

    public static final String DATE_LIMIT_BEGIN = "01/01/1920";
    public static final String DATE_LIMIT_END = "01/01/2120";

    @Override
    public Date deserialize(JsonParser jsonparser, DeserializationContext deserializationcontext) throws IOException {
        SimpleDateFormat dateFormat = DateTimeUtil.configureDateFormat(DatePatternEnum.DATE_FORMAT_dd_mm_yyyy_WITH_SLASH.getValue());
        String informedStringDate = jsonparser.getText();

        if (informedStringDate != null && !informedStringDate.isEmpty()) {
            try {
                Date informedFormattedDate = dateFormat.parse(informedStringDate);
                if (!DateTimeUtil.betweenEqual(informedFormattedDate, dateFormat.parse(DATE_LIMIT_BEGIN), dateFormat.parse(DATE_LIMIT_END))) {
                    throw new DateRangeException(informedStringDate, DATE_LIMIT_BEGIN, DATE_LIMIT_END);
                }
                return informedFormattedDate;
            } catch (ParseException e) {
                throw new JsonParseException(jsonparser, informedStringDate + " não é uma data válida.");
            }
        }

        return null;
    }
}