package com.fiap.tech.challenge.domain.user.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserConstraintEnum {

    @FieldNameConstants.Include T_USER_TOKEN_HASH_ID_UK("O hash id informado para o usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TOKEN_EMAIL_UK("O email informado para o usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TOKEN_LOGIN_ID_UK("O login informado para o usuário já encontra-se cadastrado.");

    private final String errorMessage;
}