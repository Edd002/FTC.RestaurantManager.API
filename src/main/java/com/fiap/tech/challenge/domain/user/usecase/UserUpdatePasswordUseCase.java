package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;

public class UserUpdatePasswordUseCase {

    private final User user;

    public UserUpdatePasswordUseCase(User actualPasswordUser, UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO) {
        this.user = actualPasswordUser;
        if (!this.user.getPassword().equals(userUpdatePasswordPatchRequestDTO.getActualPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada é diferente da senha atual.");
        }
        if (this.user.getPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada não pode ser igual a senha nova.");
        }
        if (!userUpdatePasswordPatchRequestDTO.getNewPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPasswordConfirmation())) {
            throw new InvalidUserPasswordException("A senha nova é difente da confirmação de senha nova.");
        }
        this.user.setPassword(userUpdatePasswordPatchRequestDTO.getNewPassword());
    }

    public User getBuiltedUser() {
        return this.user;
    }
}