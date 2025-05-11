package com.fiap.tech.challenge.domain.user.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public class UserConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return UserConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}