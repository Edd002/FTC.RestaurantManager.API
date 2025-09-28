package com.fiap.tech.challenge.domain.unit.user;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.factory.UserFactory;
import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.dto.UserPutRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.usecase.UserUpdateUseCase;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeUpdatedException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

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
    private AutoCloseable openMocks;

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
        when(loggedUser.getAddress()).thenReturn(RestaurantTestFactory.loadDefaultAddress());

        new UserUpdateUseCase(loggedUser, userType, city, userPutRequestDTO);

        verify(loggedUser).rebuild(anyString(), anyString(), anyString(), any(UserType.class), any());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar atualizar um usuário dono de restaurante para outro tipo quando ele tem restaurantes associados")
    void shouldNotUpdateOwnerUserToClient(){
        userType = UserTypeFactory.loadEntityUserTypeOwner();
        userPutRequestDTO = UserFactory.loadInvalidOwnerToClientUserPutRequestDTO();
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeOwner());
        when(loggedUser.getRestaurantUsers()).thenReturn(List.of(mock(RestaurantUser.class)));

        EntityCannotBeUpdatedException exceptionMessage = assertThrows(EntityCannotBeUpdatedException.class, () -> new UserUpdateUseCase(loggedUser, userType, city, userPutRequestDTO));

        verify(loggedUser, times(0)).rebuild(anyString(), anyString(), anyString(), any(UserType.class), any());
        assertEquals("O usuário é um dono de restaurante com restaurantes associados e por isso não pode ter seu tipo alterado.", exceptionMessage.getMessage());
    }
}