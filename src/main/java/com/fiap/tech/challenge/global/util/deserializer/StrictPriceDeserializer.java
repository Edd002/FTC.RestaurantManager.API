package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

public class StrictPriceDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonToken token = jsonParser.currentToken();
        if (!token.isNumeric()) {
            deserializationContext.reportInputMismatch(String.class, jsonParser.currentName() + " não é um valor do tipo `Double`.", token.toString());
            return null;
        }
        BigDecimal value = BigDecimal.valueOf(jsonParser.getDoubleValue());
        if (value.scale() > 2) {
            deserializationContext.reportInputMismatch(Double.class, jsonParser.currentName() + " não é um valor formatado como preço. Verifique o número de casas decimais.", token.toString());
            return null;
        }
        return value;
    }
}