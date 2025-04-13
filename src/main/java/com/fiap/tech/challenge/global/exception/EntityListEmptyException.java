package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public final class EntityListEmptyException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public EntityListEmptyException(String message) {
        super(message);
    }
}