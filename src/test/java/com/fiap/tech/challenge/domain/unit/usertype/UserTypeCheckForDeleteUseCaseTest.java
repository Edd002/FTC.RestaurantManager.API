package com.fiap.tech.challenge.domain.unit.usertype;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeCheckForDeleteUseCase;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserTypeCheckForDeleteUseCaseTest {

    AutoCloseable openMocks;
    @Mock
    private UserType userType;
    @Mock
    private User user;

    private static final String DEFAULT_USER_TYPE_EXCEPTION_MESSAGE = "Não foi possível realizar a exclusão pois tipos de usuário padrão (administrador, dono de restaurante e cliente) não podem ser excluídos.";
    private static final String USER_TYPE_WITH_USERS_EXCEPTION_MESSAGE = "Não foi possível realizar a exclusão pois existem usuários associados ao tipo.";

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception{
        openMocks.close();
    }

    @Test
    @DisplayName("Teste de sucesso - Usuário fornecido deve estar disponível para exclusão")
    void shouldUserTypeBeAllowedToDelete() {
        when(userType.getName()).thenReturn("OTHER");
        when(userType.getUsers()).thenReturn(new ArrayList<>());

        assertTrue(new UserTypeCheckForDeleteUseCase(userType).isAllowedToDelete());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao verificar a exclusão do tipo de usuário padrão(administrador, dono de restaurante e cliente)")
    void shouldUserTypeBeNotAllowedToDeleteDueToDefaultUserType() {
        when(userType.getName()).thenReturn("OWNER");

        EntityCannotBeDeletedException exception = assertThrows(EntityCannotBeDeletedException.class, () -> new UserTypeCheckForDeleteUseCase(userType));
        assertEquals(DEFAULT_USER_TYPE_EXCEPTION_MESSAGE, exception.getMessage());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao verificar a exclusão do tipo de usuário com usuários associados")
    void shouldUserTypeBeNotAllowedToDeleteDueToUsersAssociated() {
        when(userType.getName()).thenReturn("OTHER");
        when(userType.getUsers()).thenReturn(Collections.singletonList(user));

        EntityCannotBeDeletedException exception = assertThrows(EntityCannotBeDeletedException.class, () -> new UserTypeCheckForDeleteUseCase(userType));
        assertEquals(USER_TYPE_WITH_USERS_EXCEPTION_MESSAGE, exception.getMessage());
    }
}