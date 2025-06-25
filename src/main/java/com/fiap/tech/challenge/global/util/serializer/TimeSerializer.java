package com.fiap.tech.challenge.global.util.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value != null) {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
            gen.writeString(timeFormat.format(value));
        } else {
            gen.writeNull();
        }
    }

}
