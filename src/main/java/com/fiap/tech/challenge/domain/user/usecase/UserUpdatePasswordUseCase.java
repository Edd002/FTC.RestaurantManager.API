package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;
import com.fiap.tech.challenge.global.util.CryptoUtil;

public final class UserUpdatePasswordUseCase {

    private final User user;

    public UserUpdatePasswordUseCase(User actualUser, String passwordCryptoKey, UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO) {
        CryptoUtil crypto = CryptoUtil.newInstance(passwordCryptoKey);
        if (!crypto.matches(userUpdatePasswordPatchRequestDTO.getActualPassword(), actualUser.getPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada é diferente da senha atual.");
        }
        if (crypto.matches(userUpdatePasswordPatchRequestDTO.getNewPassword(), actualUser.getPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada não pode ser igual a senha nova.");
        }
        if (!userUpdatePasswordPatchRequestDTO.getNewPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPasswordConfirmation())) {
            throw new InvalidUserPasswordException("A senha nova é difente da confirmação de senha nova.");
        }
        this.user = actualUser;
        this.user.setEncryptedPassword(passwordCryptoKey, userUpdatePasswordPatchRequestDTO.getNewPassword());
    }

    public User getBuiltedUser() {
        return this.user;
    }
}