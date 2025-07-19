package com.fiap.tech.challenge.domain.jwt.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public final class JwtConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return JwtConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}