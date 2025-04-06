package com.fiap.tech.challenge.domain.usertoken.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserTokenConstraintEnum {

    @FieldNameConstants.Include T_USER_TOKEN_HASH_ID_UK("O hash id informado para o token de usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TOKEN_NAME_UK("O nome informado para o token de usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TOKEN_CLIENT_ID_UK("O id do cliente informado para o token de usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_TOKEN_CLIENT_SECRET_UK("O segredo do cliente informado para o token de usuário já encontra-se cadastrado.");

    private final String errorMessage;
}