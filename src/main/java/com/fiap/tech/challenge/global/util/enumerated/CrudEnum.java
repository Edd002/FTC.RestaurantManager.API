package com.fiap.tech.challenge.global.util.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CrudEnum {

    CREATE("Create"),
    UPDATE("Update"),
    READ("Read"),
    DELETE("Delete");

    private final String description;
}