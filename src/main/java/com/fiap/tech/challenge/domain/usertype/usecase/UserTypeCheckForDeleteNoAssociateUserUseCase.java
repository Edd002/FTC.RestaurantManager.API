package com.fiap.tech.challenge.domain.usertype.usecase;

import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;

public class UserTypeCheckForDeleteNoAssociateUserUseCase {

    private final Boolean hasNoUser;

    public UserTypeCheckForDeleteNoAssociateUserUseCase(@NonNull UserType userType) {
        if (!userType.getUsers().isEmpty()) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois existem usuários associados ao tipo.");
        }
        this.hasNoUser = true;
    }

    public Boolean hasNoUser() {
        return this.hasNoUser;
    }
}