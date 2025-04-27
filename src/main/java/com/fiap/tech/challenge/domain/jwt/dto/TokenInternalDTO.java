package com.fiap.tech.challenge.domain.jwt.dto;

import lombok.*;

@Getter
@Setter
public class TokenInternalDTO {

    private String userLogin;
    private String bearerToken;

    public TokenInternalDTO(@NonNull String userLogin, @NonNull String bearerToken) {
        this.userLogin = userLogin;
        this.bearerToken = bearerToken;
    }
}