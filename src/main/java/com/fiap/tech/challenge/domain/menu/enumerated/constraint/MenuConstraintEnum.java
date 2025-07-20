package com.fiap.tech.challenge.domain.menu.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum MenuConstraintEnum {

    @FieldNameConstants.Include T_MENU__HASH_ID_UK("O hash id informado para o menu já encontra-se cadastrado.");

    private final String errorMessage;
}