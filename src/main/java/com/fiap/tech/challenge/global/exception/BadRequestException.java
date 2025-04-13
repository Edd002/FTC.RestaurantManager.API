package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public final class BadRequestException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public BadRequestException(String message) {
        super(message);
    }
}