package com.fiap.tech.challenge.domain.jwt.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum JwtConstraintEnum {

    @FieldNameConstants.Include T_JWT_HASH_ID_UK("O hash id informado para o JWT já encontra-se cadastrado."),
    @FieldNameConstants.Include T_JWT_BEARER_TOKEN_UK("O código informado para o JWT já encontra-se cadastrado.");

    private final String errorMessage;
}