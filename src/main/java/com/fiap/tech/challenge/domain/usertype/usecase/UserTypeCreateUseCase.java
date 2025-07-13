package com.fiap.tech.challenge.domain.usertype.usecase;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import lombok.NonNull;

public class UserTypeCreateUseCase {

    private final UserType userType;

    public UserTypeCreateUseCase(@NonNull UserTypePostRequestDTO userTypePostRequestDTO) {
        this.userType = buildUserType(userTypePostRequestDTO);
    }

    private UserType buildUserType(UserTypePostRequestDTO userTypePostRequestDTO) {
        return new UserType(userTypePostRequestDTO.getName());
    }

    public UserType getBuiltedUserType() {
        return this.userType;
    }
}