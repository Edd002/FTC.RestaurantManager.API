package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse412;

import java.util.List;

public final class PreConditionFailedException extends ApiException {

    public PreConditionFailedException() {
        super("Falha na condição pré estabelecida.");
    }

    public PreConditionFailedException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse412(List.of(super.getMessage()));
    }
}