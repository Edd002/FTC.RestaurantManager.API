package com.fiap.tech.challenge.domain.state.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public final class StateConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return StateConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}