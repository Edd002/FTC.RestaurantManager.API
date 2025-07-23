package com.fiap.tech.challenge.domain.unit.user;

import com.fiap.tech.challenge.domain.factory.UserFactory;
import com.fiap.tech.challenge.domain.user.dto.UserUpdatePasswordPatchRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdatePasswordUseCase;
import com.fiap.tech.challenge.global.exception.InvalidUserPasswordException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserUpdatePasswordUseCaseTest {

    private static final String cryptoKey = "5E50F405ACE6CBDF17379F4B9F2B0C9F4144C5E380EA0B9298CB02EBD8FFE511";
    private static final String DIFFERENT_ACTUAL_PASSWORD_EXCEPTION = "A senha cadastrada é diferente da senha atual.";
    private static final String EQUAL_PASSWORDS_EXCEPTION = "A senha cadastrada não pode ser igual a senha nova.";
    private static final String PASSWORD_DIFFERENT_FROM_CONFIRMATION_EXCEPTION = "A senha nova é difente da confirmação de senha nova.";

    AutoCloseable openMocks;

    @Mock
    private User loggedUser;

    UserUpdatePasswordPatchRequestDTO userUpdatePasswordPatchRequestDTO;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - Deve atualizar a senha de um usuário")
    void shouldUpdateUserPassword(){
        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);
        userUpdatePasswordPatchRequestDTO = UserFactory.loadValidPatchRequestDTO();
        when(loggedUser.getPassword()).thenReturn(crypto.encode(userUpdatePasswordPatchRequestDTO.getActualPassword()));

        new UserUpdatePasswordUseCase(loggedUser, userUpdatePasswordPatchRequestDTO, cryptoKey);

        verify(loggedUser).setEncryptedPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao utilizar uma senha atual diferente da cadastrada")
    void shouldNotUpdateUserPasswordWithWrongActualPassword(){
        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);
        userUpdatePasswordPatchRequestDTO = UserFactory.loadInvalidWrongPasswordPatchRequestDTO();
        when(loggedUser.getPassword()).thenReturn(crypto.encode("client"));

        InvalidUserPasswordException exception = assertThrows(InvalidUserPasswordException.class, () ->  new UserUpdatePasswordUseCase(loggedUser, userUpdatePasswordPatchRequestDTO, cryptoKey));
        assertEquals(DIFFERENT_ACTUAL_PASSWORD_EXCEPTION, exception.getMessage());
        verify(loggedUser, times(0)).setEncryptedPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar atualizar uma senha para a mesma da atual")
    void shouldNotUpdateUserPasswordWithSameNewAndActualPassword(){
        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);
        userUpdatePasswordPatchRequestDTO = UserFactory.loadInvalidSamePasswordPatchRequestDTO();
        when(loggedUser.getPassword()).thenReturn(crypto.encode(userUpdatePasswordPatchRequestDTO.getNewPassword()));

        InvalidUserPasswordException exception = assertThrows(InvalidUserPasswordException.class, () ->  new UserUpdatePasswordUseCase(loggedUser, userUpdatePasswordPatchRequestDTO, cryptoKey));
        assertEquals(EQUAL_PASSWORDS_EXCEPTION, exception.getMessage());
        verify(loggedUser, times(0)).setEncryptedPassword(anyString(), anyString());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar utilizar confirmação de senha diferente da nova")
    void shouldNotUpdateUserPasswordWithWrongPasswordConfirmation(){
        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);
        userUpdatePasswordPatchRequestDTO = UserFactory.loadInvalidWrongPasswordConfirmationPatchRequestDTO();
        when(loggedUser.getPassword()).thenReturn(crypto.encode(userUpdatePasswordPatchRequestDTO.getActualPassword()));

        InvalidUserPasswordException exception = assertThrows(InvalidUserPasswordException.class, () ->  new UserUpdatePasswordUseCase(loggedUser, userUpdatePasswordPatchRequestDTO, cryptoKey));
        assertEquals(PASSWORD_DIFFERENT_FROM_CONFIRMATION_EXCEPTION, exception.getMessage());
        verify(loggedUser, times(0)).setEncryptedPassword(anyString(), anyString());
    }
}
