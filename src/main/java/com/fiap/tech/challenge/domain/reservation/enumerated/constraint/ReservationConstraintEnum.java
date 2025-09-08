package com.fiap.tech.challenge.domain.reservation.enumerated.constraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldNameConstants;

@Getter
@RequiredArgsConstructor
@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum ReservationConstraintEnum {

    @FieldNameConstants.Include T_RESERVATION__HASH_ID_UK("O hash id informado para a reserva já encontra-se cadastrado."),
    @FieldNameConstants.Include T_RESERVATION__FK_RESTAURANT_AND_FK_USER_AND_B_TIM_AND_B_DAT_UK("O usuário não pode cadastrar uma nova reserva para o mesmo restaurante no mesmo horário e no mesmo dia."),
    @FieldNameConstants.Include T_RESERVATION__BOOKING_STATUS_CHECK("O status informado para a reserva não é uma opção válida."),
    @FieldNameConstants.Include T_RESERVATION__BOOKING_TIME_CHECK("O horário informado para a reserva não é uma opção válida.");

    private final String errorMessage;
}