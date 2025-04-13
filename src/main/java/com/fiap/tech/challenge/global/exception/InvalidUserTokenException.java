package com.fiap.tech.challenge.global.exception;

import org.hibernate.service.spi.ServiceException;

public final class InvalidUserTokenException extends ServiceException {

    public InvalidUserTokenException() {
        super("Token de usuário inválido.");
    }

    public InvalidUserTokenException(String message) {
        super("Token de usuário inválido. " + message);
    }
}