package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import lombok.NonNull;

public final class UserUpdatePasswordUseCase {

    private final User user;

    public UserUpdatePasswordUseCase(@NonNull User loggedlUser, @NonNull UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO, @NonNull String passwordCryptoKey) {
        CryptoUtil crypto = CryptoUtil.newInstance(passwordCryptoKey);
        if (!crypto.matches(userUpdatePasswordPatchRequestDTO.getActualPassword(), loggedlUser.getPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada é diferente da senha atual.");
        }
        if (crypto.matches(userUpdatePasswordPatchRequestDTO.getNewPassword(), loggedlUser.getPassword())) {
            throw new InvalidUserPasswordException("A senha cadastrada não pode ser igual a senha nova.");
        }
        if (!userUpdatePasswordPatchRequestDTO.getNewPassword().equals(userUpdatePasswordPatchRequestDTO.getNewPasswordConfirmation())) {
            throw new InvalidUserPasswordException("A senha nova é difente da confirmação de senha nova.");
        }
        this.user = loggedlUser;
        this.user.setEncryptedPassword(passwordCryptoKey, userUpdatePasswordPatchRequestDTO.getNewPassword());
    }

    public User getBuiltedUser() {
        return this.user;
    }
}