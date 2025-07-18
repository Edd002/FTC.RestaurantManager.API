package com.fiap.tech.challenge.domain.user.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum UserConstraintEnum {

    @FieldNameConstants.Include T_USER_HASH_ID_UK("O hash id informado para o usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_EMAIL_UK("O email informado para o usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_LOGIN_UK("O login informado para o usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_USER_FK_ADDRESS_UK("O endereço informado para o usuário já encontra-se vinculado à outro usuário.");

    private final String errorMessage;
}