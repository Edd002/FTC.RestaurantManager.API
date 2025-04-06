package com.fiap.tech.challenge.domain.state.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum StateConstraintEnum {

    @FieldNameConstants.Include T_STATE_HASH_ID_UK("O hash id informado para o estado já encontra-se cadastrado."),
    @FieldNameConstants.Include T_STATE_NAME_UK("O nome informado para o estado já encontra-se cadastrado."),
    @FieldNameConstants.Include T_STATE_UF_UK("A UF informada para o estado já encontra-se cadastrada.");

    private final String errorMessage;
}