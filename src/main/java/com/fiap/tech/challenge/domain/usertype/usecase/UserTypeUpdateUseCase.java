package com.fiap.tech.challenge.domain.usertype.usecase;

import com.fiap.tech.challenge.domain.user.enumerated.DefaultUserTypeEnum;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import lombok.NonNull;
import org.apache.commons.lang3.EnumUtils;

public class UserTypeUpdateUseCase {

    private final UserType userType;

    public UserTypeUpdateUseCase(@NonNull UserType existingUserType, @NonNull UserTypePutRequestDTO userTypePutRequestDTO) {
        if (EnumUtils.isValidEnum(DefaultUserTypeEnum.class, existingUserType.getName())) {
            throw new EntityCannotBeDeletedException("Não foi possível realizar a atualização pois tipos de usuário padrão (administrador, dono de restaurante e cliente) não podem ser atualizados.");
        }
        this.userType = rebuildUserType(existingUserType, userTypePutRequestDTO);
    }

    private UserType rebuildUserType(UserType existingUserType, UserTypePutRequestDTO userTypePutRequestDTO) {
        return existingUserType.rebuild(userTypePutRequestDTO.getName());
    }

    public UserType getRebuiltedUserType() {
        return this.userType;
    }
}