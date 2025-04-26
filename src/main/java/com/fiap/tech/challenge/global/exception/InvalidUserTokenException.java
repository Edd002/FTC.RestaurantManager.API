package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;

import java.util.List;

public final class InvalidUserTokenException extends ApiException {

    public InvalidUserTokenException() {
        super("Token de usuário inválido.");
    }

    public InvalidUserTokenException(String message) {
        super("Token de usuário inválido. " + message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse401(List.of(super.getMessage()));
    }
}