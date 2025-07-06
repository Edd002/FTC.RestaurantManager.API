package com.fiap.tech.challenge.domain.menuitem.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum MenuItemConstraintEnum {

    @FieldNameConstants.Include T_MENU_ITEM_HASH_ID_UK("O hash id informado para o item do menu já encontra-se cadastrado."),
    @FieldNameConstants.Include T_MENU_ITEM_NAME_AND_FK_MENU_UK("O nome informado para o item do menu já encontra-se cadastrado para o menu.");

    private final String errorMessage;
}