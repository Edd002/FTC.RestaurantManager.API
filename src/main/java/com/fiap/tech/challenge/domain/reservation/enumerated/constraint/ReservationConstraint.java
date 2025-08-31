package com.fiap.tech.challenge.domain.reservation.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public class ReservationConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return ReservationConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}