package com.fiap.tech.challenge.global.component;

import com.fiap.tech.challenge.global.util.enumerated.DatePatternEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpBodyComponent {

    public <T> T responseEntityToObject(ResponseEntity<?> response, TypeToken<T> typeToken) {
        Gson gson = new GsonBuilder().setDateFormat(DatePatternEnum.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss_SSS.getValue()).create();
        return gson.fromJson(gson.toJson(response.getBody()), typeToken.getType());
    }
}