package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public final class ConstraintNotAssociatedWithEntityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ConstraintNotAssociatedWithEntityException() {
        super();
    }

    public ConstraintNotAssociatedWithEntityException(String message) {
        super(message);
    }
}