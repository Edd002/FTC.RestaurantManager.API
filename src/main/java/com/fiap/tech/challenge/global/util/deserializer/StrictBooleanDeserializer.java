package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class StrictBooleanDeserializer extends JsonDeserializer<Boolean> {

    @Override
    public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken token = jsonParser.currentToken();
        if (token.isNumeric() || !token.isBoolean()) {
            deserializationContext.reportInputMismatch(String.class, jsonParser.currentName() + " não é um valor do tipo `Boolean`.", token.toString());
            return null;
        }
        return jsonParser.getBooleanValue();
    }
}