package com.fiap.tech.challenge.domain.unit.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.factory.UserFactory;
import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdateUseCase;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserUpdateUseCaseTest {

    private static final String cryptoKey = "5E50F405ACE6CBDF17379F4B9F2B0C9F4144C5E380EA0B9298CB02EBD8FFE511";

    @Mock
    private User loggedUser;
    @Mock
    private City city;
    @Mock
    private Address address;

    private UserType userType;
    private UserPutRequestDTO userPutRequestDTO;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - Deve atualizar as informações de um usuário")
    void shouldUpdateUser(){
        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);
        userType = UserTypeFactory.loadEntityUserTypeClient();
        userPutRequestDTO = UserFactory.loadValidClientUserPutRequestDTO();
        when(loggedUser.getPassword()).thenReturn(crypto.encode("test123"));
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeOwner());
        when(loggedUser.getAddress()).thenReturn(address);

        new UserUpdateUseCase(loggedUser, userType, city, userPutRequestDTO, cryptoKey);

        verify(loggedUser).rebuild(anyString(), anyString(), anyString(), anyString(), anyString(), any(UserType.class), any());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar atualizar um usuário do tipo cliente para dono")
    void shouldNotUpdateClientUserToOwner(){
        userType = UserTypeFactory.loadEntityUserTypeClient();
        userPutRequestDTO = UserFactory.loadValidOwnerUserPutRequestDTO();
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeClient());

        assertThrows(AuthorizationException.class, () -> new UserUpdateUseCase(loggedUser, userType, city, userPutRequestDTO, cryptoKey));

        verify(loggedUser, times(0)).rebuild(anyString(), anyString(), anyString(), anyString(), anyString(), any(UserType.class), any());
    }


}