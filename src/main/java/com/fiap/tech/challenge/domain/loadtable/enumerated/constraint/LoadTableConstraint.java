package com.fiap.tech.challenge.domain.loadtable.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public final class LoadTableConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return LoadTableConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}