package com.fiap.tech.challenge.domain.restaurant.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum RestaurantConstraintEnum {

    @FieldNameConstants.Include T_RESTAURANT__HASH_ID_UK("O hash id informado para o restaurante já encontra-se cadastrado."),
    @FieldNameConstants.Include T_RESTAURANT__FK_MENU_UK("O menu informado para o restaurante já encontra-se vinculado à outro restaurante."),
    @FieldNameConstants.Include T_RESTAURANT__FK_ADDRESS_UK("O endereço informado para o restaurante já encontra-se vinculado à outro restaurante."),
    @FieldNameConstants.Include T_RESTAURANT__TYPE_CHECK("O tipo informada para o restaurante não é uma opção válida.");

    private final String errorMessage;
}