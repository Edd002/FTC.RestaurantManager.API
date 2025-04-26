package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse500;

import java.io.Serial;
import java.util.List;

public final class HashingException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public HashingException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse500(List.of(super.getMessage()));
    }
}