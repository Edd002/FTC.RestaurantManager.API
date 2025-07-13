package com.fiap.tech.challenge.global.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tech.challenge.config.RunOnReady;
import com.fiap.tech.challenge.global.adapter.OptionalTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

@UtilityClass
public class JsonUtil {

    public static String toJson(Object object) {
        try {
            return new Gson().toJson(object);
        } catch (Exception ignore) {
            return StringUtils.EMPTY;
        }
    }

    public static <T> T toObject(String json, Class<T> type) {
        try {
            return new Gson().fromJson(json, type);
        } catch (Exception exception) {
            return null;
        }
    }

    public static boolean isValidObject(String test) {
        if (StringUtils.isBlank(test)) {
            return false;
        }
        try {
            new JSONObject(test);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static boolean isValidArray(String test) {
        if (StringUtils.isBlank(test)) {
            return false;
        }
        try {
            new JSONArray(test);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public <T> List<T> objectListFromJson(String dataGroup, String pathResource, Type listType) {
        return objectListFromJson(dataGroup, pathResource, listType, null);
    }

    public <T> List<T> objectListFromJson(String dataGroup, String pathResource, Type listType, String datePattern) {
        List<T> list = null;
        String json = getContentFromResource(dataGroup, pathResource);
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY);
        Gson gson = ValidationUtil.isNotBlank(datePattern) ? gsonBuilder.setDateFormat(datePattern).create() : gsonBuilder.create();
        list = gson.fromJson(json, listType);
        return list;
    }

    public <T> T objectFromJson(String dataGroup, String pathResource, Type type) {
        return objectFromJson(dataGroup, pathResource, type, null);
    }

    public <T> T objectFromJson(String dataGroup, String pathResource, Type type, String datePattern) {
        String json = getContentFromResource(dataGroup, pathResource);
        GsonBuilder gsonBuilder = new GsonBuilder().registerTypeAdapterFactory(OptionalTypeAdapter.FACTORY);
        Gson gson = ValidationUtil.isNotBlank(datePattern) ? gsonBuilder.setDateFormat(datePattern).create() : gsonBuilder.create();
        return gson.fromJson(json, type);
    }

    public String getContentFromResource(String dataGroup, String pathResource) {
        JSONObject jsonDataObject = null;
        try {
            InputStream stream = RunOnReady.class.getResourceAsStream(pathResource);
            jsonDataObject = new JSONObject(StreamUtils.copyToString(stream, StandardCharsets.UTF_8));
            return jsonDataObject.get(dataGroup).toString();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T loadMockJsonWithReplacement(String pathResource, String replacement, String replacementNewValue, String dataGroup, Class<T> targetClass) {
        try {
            Resource resource = new ClassPathResource(pathResource);
            String jsonContent = Files.readString(resource.getFile().toPath());
            jsonContent = jsonContent.replace(replacement, replacementNewValue);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonContent);
            return objectMapper.convertValue(rootNode.get(dataGroup), targetClass);
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
}