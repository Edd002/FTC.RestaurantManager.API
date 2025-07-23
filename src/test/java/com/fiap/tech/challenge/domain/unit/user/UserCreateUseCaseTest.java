package com.fiap.tech.challenge.domain.unit.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.factory.UserFactory;
import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.usecase.UserCreateUseCase;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserCreateUseCaseTest {

    private static final String cryptoKey = "5E50F405ACE6CBDF17379F4B9F2B0C9F4144C5E380EA0B9298CB02EBD8FFE511";

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Mock
    private User loggedUser;
    @Mock
    private City city;

    private UserType userType;
    private UserPostRequestDTO userPostRequestDTO;

    @Test
    @DisplayName("Teste de sucesso - Deve criar o usuário dono à partir de um dono já autenticado")
    void shouldCreateOwnerUserWithAuthOwnerUser(){
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeOwner());
        userType = UserTypeFactory.loadEntityUserTypeOwner();
        userPostRequestDTO = UserFactory.loadValidOwnerPostRequestDTO();
        User user = new UserCreateUseCase(loggedUser, userType, city, userPostRequestDTO, cryptoKey).getBuiltedUser();

        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);

        assertEquals("New Owner", user.getName());
        assertEquals("newowner@email.com", user.getEmail());
        assertEquals("newowner", user.getLogin());
        assertTrue(crypto.matches("newowner", user.getPassword()));
        assertEquals("OWNER", user.getType().getName());
        assertEquals("New Description 00", user.getAddress().getDescription());
        assertEquals("00", user.getAddress().getNumber());
        assertEquals("New Complement 00", user.getAddress().getComplement());
        assertEquals("New Neighborhood 00", user.getAddress().getNeighborhood());
        assertEquals("00000-000", user.getAddress().getCep());
        assertEquals("0000-0000", user.getAddress().getPostalCode());
    }

    @Test
    @DisplayName("Teste de sucesso - Deve criar um usuário que não é dono sem autenticação")
    void shouldCreateUserSucessfully(){
        userPostRequestDTO = UserFactory.loadValidClientPostRequestDTO();
        userType = UserTypeFactory.loadEntityUserTypeClient();

        User user = new UserCreateUseCase(userType, city, userPostRequestDTO, cryptoKey).getBuiltedUser();

        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);

        assertEquals("New Client", user.getName());
        assertEquals("newclient@email.com", user.getEmail());
        assertEquals("newclient", user.getLogin());
        assertTrue(crypto.matches("newclient", user.getPassword()));
        assertEquals("CLIENT", user.getType().getName());
        assertEquals("New Description", user.getAddress().getDescription());
        assertEquals("00", user.getAddress().getNumber());
        assertEquals("New Complement", user.getAddress().getComplement());
        assertEquals("New Neighborhood", user.getAddress().getNeighborhood());
        assertEquals("00000-000", user.getAddress().getCep());
        assertEquals("0000-0000", user.getAddress().getPostalCode());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar criar um usuário do tipo dono com um usuário cliente autenticado")
    void shouldNotCreateOwnerUserWithAuthClientUser(){
        when(loggedUser.getType()).thenReturn(UserTypeFactory.loadEntityUserTypeClient());
        userType = UserTypeFactory.loadEntityUserTypeClient();
        userPostRequestDTO = UserFactory.loadValidOwnerPostRequestDTO();

        assertThrows(AuthorizationException.class, () -> new UserCreateUseCase(loggedUser, userType, city, userPostRequestDTO, cryptoKey));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar criar um usuário dono sem autenticação")
    void shouldNotCreateOwnerUserWithoutAuth(){
        userType = UserTypeFactory.loadEntityUserTypeClient();
        userPostRequestDTO = UserFactory.loadValidOwnerPostRequestDTO();

        assertThrows(AuthorizationException.class, () -> new UserCreateUseCase(userType, city, userPostRequestDTO, cryptoKey));
    }
}