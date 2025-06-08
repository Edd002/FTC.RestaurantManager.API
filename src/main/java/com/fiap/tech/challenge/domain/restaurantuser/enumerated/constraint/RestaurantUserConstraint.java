package com.fiap.tech.challenge.domain.restaurantuser.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public class RestaurantUserConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return RestaurantUserConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}