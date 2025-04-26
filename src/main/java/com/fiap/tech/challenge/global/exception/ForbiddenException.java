package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse403;

import java.util.List;

public final class ForbiddenException extends ApiException {

    public ForbiddenException() {
        super("Acesso negado.");
    }

    public ForbiddenException(String message) {
        super("Acesso negado. " + message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse403(List.of(super.getMessage()));
    }
}