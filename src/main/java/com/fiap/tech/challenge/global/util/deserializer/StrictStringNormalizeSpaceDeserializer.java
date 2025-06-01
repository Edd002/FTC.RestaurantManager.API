package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class StrictStringNormalizeSpaceDeserializer extends StringDeserializer {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken token = jsonParser.currentToken();
        if (token.isBoolean() || token.isNumeric() || !token.toString().equalsIgnoreCase("VALUE_STRING")) {
            deserializationContext.reportInputMismatch(String.class, jsonParser.currentName() + " não é um valor do tipo `String`.", token.toString());
            return null;
        }
        return StringUtils.normalizeSpace(super.deserialize(jsonParser, deserializationContext));
    }
}