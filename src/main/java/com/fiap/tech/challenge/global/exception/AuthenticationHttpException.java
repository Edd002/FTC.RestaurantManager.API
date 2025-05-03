package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse401;

import java.io.Serial;
import java.util.List;

public class AuthenticationHttpException extends ApiException {

  @Serial
  private static final long serialVersionUID = 1L;

  public AuthenticationHttpException() {
    super("Cliente não autenticado.");
  }

  public AuthenticationHttpException(String message) {
    super(message);
  }

  @Override
  public BaseErrorResponse getBaseErrorResponse() {
    return new BaseErrorResponse401(List.of(super.getMessage()));
  }
}