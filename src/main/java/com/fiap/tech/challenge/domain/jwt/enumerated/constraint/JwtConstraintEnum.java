package com.fiap.tech.challenge.domain.jwt.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum JwtConstraintEnum {

    @FieldNameConstants.Include T_JWT__HASH_ID_UK("O hash id informado para o JWT já encontra-se cadastrado."),
    @FieldNameConstants.Include T_JWT__BEARER_TOKEN_UK("O bearer token informado para o JWT já encontra-se cadastrado.");

    private final String errorMessage;
}