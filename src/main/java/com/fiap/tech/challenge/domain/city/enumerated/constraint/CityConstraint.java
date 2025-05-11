package com.fiap.tech.challenge.domain.city.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public final class CityConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return CityConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}