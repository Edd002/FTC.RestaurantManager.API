package com.fiap.tech.challenge.domain.address.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum AddressConstraintEnum {

    @FieldNameConstants.Include T_ADDRESS_HASH_ID_UK("O hash id informado para o endereço já encontra-se cadastrado.");

    private final String errorMessage;
}