package com.fiap.tech.challenge.global.entity.enumerated;

public enum SortOrderEnum {

    ASC,
    DESC,
    EMPTY,
    BOTH;

    public static SortOrderEnum valueOfIgnoreCase(String sortOrder) {
        return valueOf(sortOrder.toUpperCase());
    }
}