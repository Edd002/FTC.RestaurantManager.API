package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public final class EntityNullException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityNullException(String message) {
        super(message);
    }
}