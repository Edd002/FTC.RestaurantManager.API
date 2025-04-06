package com.fiap.tech.challenge.domain.city.enumerated;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum CityConstraintEnum {

    @FieldNameConstants.Include T_CITY_HASH_ID_UK("O hash id informado para a cidade já encontra-se cadastrado."),
    @FieldNameConstants.Include T_CITY_FK_STATE_AND_NOME_UK("O nome informado para a cidade já encontra-se cadastrado para o estado.");

    private final String errorMessage;
}