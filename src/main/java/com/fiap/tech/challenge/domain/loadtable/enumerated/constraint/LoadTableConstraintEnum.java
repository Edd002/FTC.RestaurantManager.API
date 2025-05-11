package com.fiap.tech.challenge.domain.loadtable.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum LoadTableConstraintEnum {

    @FieldNameConstants.Include T_LOAD_TABLE_HASH_ID_UK("O hash id informado para a carga de tabela já encontra-se cadastrado."),
    @FieldNameConstants.Include T_LOAD_TABLE_ENTITY_NAME_UK("A nome da entidade informado para a carga de tabela já encontra-se cadastrado.");

    private final String errorMessage;
}