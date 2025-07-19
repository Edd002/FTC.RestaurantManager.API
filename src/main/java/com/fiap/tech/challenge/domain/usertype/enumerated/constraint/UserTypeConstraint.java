package com.fiap.tech.challenge.domain.usertype.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public class UserTypeConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return UserTypeConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}