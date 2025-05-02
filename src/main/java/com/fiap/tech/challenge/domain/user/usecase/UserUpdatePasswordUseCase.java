package com.fiap.tech.challenge.domain.user.usecase;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.global.exception.CriptoException;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public final class UserUpdatePasswordUseCase {

    @Value("${cripto.key}")
    private String criptoKey;

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
        try {
            this.user.setPassword(criptoKey, userUpdatePasswordPatchRequestDTO.getNewPassword());
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeySpecException | UnsupportedEncodingException | IllegalBlockSizeException | BadPaddingException e) {
            throw new CriptoException("Erro ao gerar senha criptografada do usuário.");
        } catch (Exception exception) {
            throw new CriptoException("Ocorreu um erro na alteração de senha do usuário.");
        }

    }

    public User getBuiltedUser() {
        return this.user;
    }
}