package com.fiap.tech.challenge.domain.jwt.usecase;

import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.global.util.DateTimeUtil;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public class JwtIsActiveUseCase {

    private final Boolean isActive;

    public JwtIsActiveUseCase(Jwt jwt, int millisecondsToExpireJwt) {
        this.isActive = ValidationUtil.isNotNull(jwt) && DateTimeUtil.beforeNow(DateTimeUtil.addMilliseconds(jwt.getUpdatedIn(), millisecondsToExpireJwt));
    }

    public Boolean isActive() {
        return this.isActive;
    }
}