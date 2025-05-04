package com.fiap.tech.challenge.domain.jwt.usecase;

import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.global.exception.InvalidBearerTokenHttpException;

import java.util.Date;

public class JwtRefreshByBearerTokenUserCase {

    private final Jwt jwt;

    public JwtRefreshByBearerTokenUserCase(Jwt existingJwt, int millisecondsToExpireJwt) {
        if (!new JwtIsActiveUseCase(existingJwt, millisecondsToExpireJwt).isActive()) {
            throw new InvalidBearerTokenHttpException();
        }
        this.jwt = existingJwt;
        this.jwt.setUpdatedIn(new Date());
    }

    public Jwt getRefreshedJwt() {
        return this.jwt;
    }
}