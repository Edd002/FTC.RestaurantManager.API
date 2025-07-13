package com.fiap.tech.challenge.global.util.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

public class StrictStringNormalizeSpaceUpperCaseDeserializer extends StrictStringNormalizeSpaceDeserializer {

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        return StringUtils.upperCase(super.deserialize(jsonParser, deserializationContext));
    }
}