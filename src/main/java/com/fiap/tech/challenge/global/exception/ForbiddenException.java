package com.fiap.tech.challenge.global.exception;

import org.hibernate.service.spi.ServiceException;

public final class ForbiddenException extends ServiceException {

    public ForbiddenException() {
        super("Acesso negado.");
    }

    public ForbiddenException(String message) {
        super("Acesso negado. " + message);
    }
}