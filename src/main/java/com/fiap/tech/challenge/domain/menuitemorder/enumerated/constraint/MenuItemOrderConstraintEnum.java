package com.fiap.tech.challenge.domain.menuitemorder.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum MenuItemOrderConstraintEnum {

    @FieldNameConstants.Include T_MENU_ITEM_ORDER__HASH_ID_UK("O hash id informado para o item de menu associado ao pedido já encontra-se cadastrado.");

    private final String errorMessage;
}