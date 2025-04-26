package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;

import java.util.List;

public final class UnauthorizedException extends ApiException {

    public UnauthorizedException() {
        super("Não autorizado.");
    }

    public UnauthorizedException(String token) {
        super(String.format("Token não autorizado: %s", token));
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse401(List.of(super.getMessage()));
    }
}