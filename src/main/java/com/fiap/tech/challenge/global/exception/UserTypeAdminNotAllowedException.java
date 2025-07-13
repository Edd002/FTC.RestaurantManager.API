package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;

import java.io.Serial;
import java.util.List;

public class UserTypeAdminNotAllowedException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserTypeAdminNotAllowedException() {
        super();
    }

    public UserTypeAdminNotAllowedException(String message) {
        super(message);
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse400(List.of(super.getMessage()));
    }
}
