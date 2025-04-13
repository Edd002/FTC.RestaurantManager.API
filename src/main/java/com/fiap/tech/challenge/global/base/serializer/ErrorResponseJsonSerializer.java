package com.fiap.tech.challenge.global.base.serializer;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public final class ErrorResponseJsonSerializer implements JsonSerializer<BaseErrorResponse> {

    @Override
    public JsonElement serialize(BaseErrorResponse baseErrorResponse, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("success", context.serialize(false));
        object.add("status", context.serialize(baseErrorResponse.getStatus()));
        object.add("path", context.serialize(baseErrorResponse.getPath()));
        object.add("reason", context.serialize(baseErrorResponse.getReason()));
        object.add("timestamp", context.serialize(baseErrorResponse.getTimestamp()));
        object.add("error", context.serialize(baseErrorResponse.getError()));
        object.add("messages", context.serialize(baseErrorResponse.getMessages()));
        return object;
    }
}