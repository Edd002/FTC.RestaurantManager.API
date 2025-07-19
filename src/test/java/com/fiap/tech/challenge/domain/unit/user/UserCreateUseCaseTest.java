package com.fiap.tech.challenge.domain.unit.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fiap.tech.challenge.domain.address.dto.AddressPostRequestDTO;
import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.domain.user.dto.UserPostRequestDTO;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.usecase.UserCreateUseCase;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import com.fiap.tech.challenge.global.util.CryptoUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserCreateUseCaseTest {

    private static final String cryptoKey = "5E50F405ACE6CBDF17379F4B9F2B0C9F4144C5E380EA0B9298CB02EBD8FFE511";

    @Mock
    private User loggedUser;
    @Mock
    private UserType loggedUserType;
    @Mock
    private UserType userType;
    @Mock
    private City city;
    @Mock
    private UserPostRequestDTO userPostRequestDTO;
    @Mock
    private AddressPostRequestDTO dtoAddress;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - Deve criar o usuário dono à partir de um dono já autenticado")
    void shouldCreateOwnerUserWithAuthOwnerUser(){
        when(userPostRequestDTO.getName()).thenReturn("Usuário teste");
        when(userPostRequestDTO.getEmail()).thenReturn("teste@teste.com");
        when(userPostRequestDTO.getLogin()).thenReturn("teste");
        when(userPostRequestDTO.getPassword()).thenReturn("teste123");

        when(loggedUserType.getName()).thenReturn("OWNER");

        when(loggedUser.getType()).thenReturn(loggedUserType);

        when(userType.getName()).thenReturn("OWNER");

        when(dtoAddress.getDescription()).thenReturn("Rua das Flores");
        when(dtoAddress.getNumber()).thenReturn("456");
        when(dtoAddress.getComplement()).thenReturn("Sala 2");
        when(dtoAddress.getNeighborhood()).thenReturn("Bela Vista");
        when(dtoAddress.getCep()).thenReturn("01310-000");
        when(dtoAddress.getPostalCode()).thenReturn("01310000");

        when(userPostRequestDTO.getAddress()).thenReturn(dtoAddress);

        User user = new UserCreateUseCase(loggedUser, userType, city, userPostRequestDTO, cryptoKey).getBuiltedUser();

        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);

        assertEquals("Usuário teste", user.getName());
        assertEquals("teste@teste.com", user.getEmail());
        assertEquals("teste", user.getLogin());
        assertTrue(crypto.matches("teste123", user.getPassword()));
        assertEquals("OWNER", user.getType().getName());
        assertEquals("Rua das Flores", user.getAddress().getDescription());
        assertEquals("456", user.getAddress().getNumber());
        assertEquals("Sala 2", user.getAddress().getComplement());
        assertEquals("Bela Vista", user.getAddress().getNeighborhood());
        assertEquals("01310-000", user.getAddress().getCep());
        assertEquals("01310000", user.getAddress().getPostalCode());
    }

    @Test
    @DisplayName("Teste de sucesso - Deve criar um usuário que não é dono sem autenticação")
    void shouldCreateUserSucessfully(){
        when(userPostRequestDTO.getName()).thenReturn("Usuário teste");
        when(userPostRequestDTO.getEmail()).thenReturn("teste@teste.com");
        when(userPostRequestDTO.getLogin()).thenReturn("teste");
        when(userPostRequestDTO.getPassword()).thenReturn("teste123");

        when(userType.getName()).thenReturn("CLIENT");

        when(dtoAddress.getDescription()).thenReturn("Rua das Flores");
        when(dtoAddress.getNumber()).thenReturn("456");
        when(dtoAddress.getComplement()).thenReturn("Sala 2");
        when(dtoAddress.getNeighborhood()).thenReturn("Bela Vista");
        when(dtoAddress.getCep()).thenReturn("01310-000");
        when(dtoAddress.getPostalCode()).thenReturn("01310000");

        when(userPostRequestDTO.getAddress()).thenReturn(dtoAddress);

        User user = new UserCreateUseCase(userType, city, userPostRequestDTO, cryptoKey).getBuiltedUser();

        CryptoUtil crypto = CryptoUtil.newInstance(cryptoKey);

        assertEquals("Usuário teste", user.getName());
        assertEquals("teste@teste.com", user.getEmail());
        assertEquals("teste", user.getLogin());
        assertTrue(crypto.matches("teste123", user.getPassword()));
        assertEquals("CLIENT", user.getType().getName());
        assertEquals("Rua das Flores", user.getAddress().getDescription());
        assertEquals("456", user.getAddress().getNumber());
        assertEquals("Sala 2", user.getAddress().getComplement());
        assertEquals("Bela Vista", user.getAddress().getNeighborhood());
        assertEquals("01310-000", user.getAddress().getCep());
        assertEquals("01310000", user.getAddress().getPostalCode());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao tentar criar um usuário do tipo dono com um usuário cliente autenticado")
    void shouldNotCreateOwnerUserWithAuthClientUser(){
        when(loggedUserType.getName()).thenReturn("CLIENT");
        when(userPostRequestDTO.getType()).thenReturn("OWNER");

        when(loggedUser.getType()).thenReturn(loggedUserType);
        assertThrows(AuthorizationException.class, () -> new UserCreateUseCase(loggedUser, userType, city, userPostRequestDTO, cryptoKey));
    }
}