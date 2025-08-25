package com.fiap.tech.challenge.domain.reservation.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum ReservationConstraintEnum {

    @FieldNameConstants.Include T_RESERVATION__HASH_ID_UK("O hash id informado para a reserva já encontra-se cadastrado.");

    private final String errorMessage;
}