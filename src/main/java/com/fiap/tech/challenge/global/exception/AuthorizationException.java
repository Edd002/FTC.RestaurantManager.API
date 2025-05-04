package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;

import java.io.Serial;
import java.util.List;

public class AuthorizationException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public AuthorizationException() {
        super("Usuário não tem permissão para realizar esta operação.");
    }

    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse403(List.of(super.getMessage()));
    }
}