package com.fiap.tech.challenge.domain.usertype.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserTypeConstraintEnum {

    @FieldNameConstants.Include T_USER_TYPE_HASH_ID_UK("O hash id informado para o tipo de usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TYPE_NAME_UK("O nome informado para o tipo de usuário já encontra-se cadastrado.");

    private final String errorMessage;
}