package com.fiap.tech.challenge.global.exception;

public final class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super("Não autorizado.");
    }

    public UnauthorizedException(String token) {
        super(String.format("Token não autorizado: %s", token));
    }
}