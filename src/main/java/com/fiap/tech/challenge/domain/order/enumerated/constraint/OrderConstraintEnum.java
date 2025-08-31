package com.fiap.tech.challenge.domain.order.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum OrderConstraintEnum {

    @FieldNameConstants.Include T_ORDER__HASH_ID_UK("O hash id informado para o pedido já encontra-se cadastrado."),
    @FieldNameConstants.Include T_ORDER__STATUS_CHECK("O status informada para o pedido não é uma opção válida."),
    @FieldNameConstants.Include T_ORDER__TYPE_CHECK("O tipo informada para o pedido não é uma opção válida.");

    private final String errorMessage;
}