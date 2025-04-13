package com.fiap.tech.challenge.global.exception;

public final class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException() {
        super("Falha no processamento de entidade.");
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}