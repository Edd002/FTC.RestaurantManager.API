package com.fiap.tech.challenge.domain.restaurant.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public class RestaurantConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return RestaurantConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}