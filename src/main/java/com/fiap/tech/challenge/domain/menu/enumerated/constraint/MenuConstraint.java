package com.fiap.tech.challenge.domain.menu.enumerated.constraint;

import com.fiap.tech.challenge.global.audit.constraint.IConstraint;

public class MenuConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return MenuConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}