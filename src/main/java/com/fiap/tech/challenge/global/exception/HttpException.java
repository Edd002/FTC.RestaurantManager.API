package com.fiap.tech.challenge.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter
public final class HttpException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus httpStatus;

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}