package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;

public final class UserUpdatePasswordUseCase {

    private final User user;

    public UserUpdatePasswordUseCase(User actualUser, String passwordCryptoKey, UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO) {
        if (!actualUser.getPassword().equals(userUpdatePasswordPatchRequestDTO.getActualPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada é diferente da senha atual.");
        }
        if (actualUser.getPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada não pode ser igual a senha nova.");
        }
        if (!userUpdatePasswordPatchRequestDTO.getNewPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPasswordConfirmation())) {
            throw new InvalidUserPasswordException("A senha nova é difente da confirmação de senha nova.");
        }
        this.user = new User(actualUser.getId(), passwordCryptoKey, userUpdatePasswordPatchRequestDTO.getNewPassword());
    }

    public User getBuiltedUser() {
        return this.user;
    }
}