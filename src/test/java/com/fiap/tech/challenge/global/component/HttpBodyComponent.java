package com.fiap.tech.challenge.global.component;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HttpBodyComponent {

    public <T> T responseEntityToObject(ResponseEntity<?> response, TypeToken<T> typeToken) {
        return responseEntityToObject(response, typeToken, DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS);
    }

    public <T> T responseEntityToObject(ResponseEntity<?> response, TypeToken<T> typeToken, DatePatternEnum datePatternEnum) {
        return responseEntityToObject(response, typeToken, datePatternEnum, null);
    }

    public <T> T responseEntityToObject(ResponseEntity<?> response, TypeToken<T> typeToken, DatePatternEnum datePatternEnum, JsonDeserializer<Date> DateDeserializer) {
        GsonBuilder gsonBuilder = new GsonBuilder().setDateFormat(datePatternEnum.getValue());
        Gson gson = ValidationUtil.isNotNull(DateDeserializer) ? gsonBuilder.registerTypeAdapter(Date.class, DateDeserializer).create() : gsonBuilder.create();
        return gson.fromJson(gson.toJson(response.getBody()), typeToken.getType());
    }
}