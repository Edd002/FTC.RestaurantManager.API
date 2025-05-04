package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;

public class UserCheckLoggedOwnerUseCase {

    private final Boolean isLoggedOwner;

    public UserCheckLoggedOwnerUseCase() {
        this.isLoggedOwner = AuthUserContextHolder.isOwnerAuthUser();
    }

    public Boolean isLoggedOwner() {
        return this.isLoggedOwner;
    }
}