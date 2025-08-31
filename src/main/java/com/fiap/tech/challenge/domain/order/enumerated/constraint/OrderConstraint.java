package com.fiap.tech.challenge.domain.order.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public class OrderConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return OrderConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}