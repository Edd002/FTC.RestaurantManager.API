package com.fiap.tech.challenge.domain.usertype.usecase;

import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;
import org.apache.commons.lang3.EnumUtils;

public class UserTypeCheckForDeleteUseCase {

    private final Boolean isAllowedToDelete;

    public UserTypeCheckForDeleteUseCase(@NonNull UserType userType) {
        if (EnumUtils.isValidEnum(DefaultUserTypeEnum.class, userType.getName())) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois tipos de usuário padrão (administrador, dono de restaurante e cliente) não podem ser excluídos.");
        }
        if (!userType.getUsers().isEmpty()) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a exclusão pois existem usuários associados ao tipo.");
        }
        this.isAllowedToDelete = true;
    }

    public Boolean isAllowedToDelete() {
        return this.isAllowedToDelete;
    }
}