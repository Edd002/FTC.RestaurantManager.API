package com.fiap.tech.challenge.global.search.enumerated;

public enum SortOrderEnum {

    NONE,
    ASC,
    DESC;

    public static SortOrderEnum valueOfIgnoreCase(String sortOrder) {
        return valueOf(sortOrder.toUpperCase());
    }
}