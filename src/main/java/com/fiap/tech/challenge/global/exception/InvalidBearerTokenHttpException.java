package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;

import java.io.Serial;
import java.util.List;

public class InvalidBearerTokenHttpException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidBearerTokenHttpException() {
        super("Bearer token inválido ou expirado.");
    }

    public InvalidBearerTokenHttpException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse401(List.of(super.getMessage()));
    }
}