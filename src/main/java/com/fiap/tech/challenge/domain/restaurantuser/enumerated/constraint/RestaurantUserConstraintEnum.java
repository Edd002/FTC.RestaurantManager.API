package com.fiap.tech.challenge.domain.restaurantuser.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum RestaurantUserConstraintEnum {

    @FieldNameConstants.Include T_RESTAURANT_USER_HASH_ID_UK("O hash id informado para a relação restaurante-usuário já encontra-se cadastrado."),
    @FieldNameConstants.Include T_RESTAURANT_USER_FK_RESTAURANT_AND_FK_USER_UK("O usuário informado para o restuarante já encontra-se cadastrado para o mesmo.");

    private final String errorMessage;
}