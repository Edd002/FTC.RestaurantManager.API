package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public final class EntityNotFoundException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNotFoundException(String message) {
        super(message);
    }
}