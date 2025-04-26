package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse422;

import java.util.List;

public final class UnprocessableEntityException extends ApiException {

    public UnprocessableEntityException() {
        super("Falha no processamento de entidade.");
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse422(List.of(super.getMessage()));
    }
}