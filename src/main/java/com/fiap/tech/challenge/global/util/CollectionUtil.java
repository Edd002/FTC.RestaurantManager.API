package com.fiap.tech.challenge.global.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class CollectionUtil {

    public static boolean isInCollection(Collection<?> collection, Object object) {
        return ValidationUtil.isNotNull(object) && collection.contains(object);
    }

    public static boolean isOnlyOneInCollection(Collection<?> collection, Object object) {
        return isInCollection(collection, object) && collection.size() == 1;
    }

    public static boolean containsInAnyElement(String str, List<String> list) {
        return containsInAnyElement(str, list.toArray(String[]::new));
    }

    public static boolean containsInAnyElement(String str, String[] list) {
        return Arrays.stream(list).anyMatch(str::contains);
    }

    public static boolean containsAtLeastOneElement(List<String> setList, List<String> subsetList) {
        return setList.stream().anyMatch(elem -> subsetList.stream().anyMatch(elem::contains));
    }

    public static boolean removeIfContainsInAnyElement(String str, List<String> list) {
        return list.removeIf(str::contains);
    }

    public static boolean removeIfNotContainsInAnyElement(String str, List<String> list) {
        return list.removeIf(elem -> !str.contains(elem));
    }

    public static String getElementIfContains(String str, List<String> list) {
        return list.stream().filter(str::contains).findAny().orElse(null);
    }

    public static List<String> getElementsIfContains(List<String> setList, List<String> subsetList) {
        return setList.stream().filter(elem -> subsetList.stream().anyMatch(elem::contains)).collect(Collectors.toList());
    }

    public static <T> List<T> removeDuplicates(List<T> list) {
        return list.stream().distinct().collect(Collectors.toList());
    }

    public static Map<String, Object> objectToMap(Object obj) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                if (value != null) map.put(field.getName(), value);
            } catch (Exception ignored) {
            }
        }
        if (obj.getClass().getSuperclass() != null) {
            for (Field field : obj.getClass().getSuperclass().getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    Object value = field.get(obj);
                    if (value != null) map.put(field.getName(), value);
                } catch (Exception ignored) {
                }
            }
        }
        return map;
    }

    public static Map<String, String> objectToMapString(Object obj) {
        return objectToMap(obj)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> String.valueOf(e.getValue())));
    }
}