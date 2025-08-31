package com.fiap.tech.challenge.domain.menuitemorder.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public class MenuItemOrderConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return MenuItemOrderConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}