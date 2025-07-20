package com.fiap.tech.challenge.domain.city.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum CityConstraintEnum {

    @FieldNameConstants.Include T_CITY__HASH_ID_UK("O hash id informado para a cidade já encontra-se cadastrado."),
    @FieldNameConstants.Include T_CITY__FK_STATE_AND_NAME_UK("O nome informado para a cidade já encontra-se cadastrado para o estado.");

    private final String errorMessage;
}