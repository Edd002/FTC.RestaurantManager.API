package com.fiap.tech.challenge.domain.address.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public final class AddressConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return AddressConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}