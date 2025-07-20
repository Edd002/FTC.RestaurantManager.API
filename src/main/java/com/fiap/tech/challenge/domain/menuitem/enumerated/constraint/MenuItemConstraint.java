package com.fiap.tech.challenge.domain.menuitem.enumerated.constraint;

import com.fiap.tech.challenge.global.constraint.IConstraint;

public class MenuItemConstraint implements IConstraint {

    @Override
    public String getErrorMessage(String constraintName) {
        return MenuItemConstraintEnum.valueOf(constraintName.toUpperCase()).getErrorMessage();
    }
}