package com.fiap.tech.challenge.domain.unit.usertype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeUpdateUseCase;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserTypeUpdateUseCaseTest {
    @Mock
    private UserTypePutRequestDTO dtoUserType;

    private static final long ID = 1L;

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
    @DisplayName("Teste de sucesso - Deve atualizar o tipo de usuário com um novo nome")
    void shouldUpdateUserTypeWithNewName(){
        UserType existingUserType = createExistingUserTypeMock("OTHER");
        when(dtoUserType.getName()).thenReturn("NEW_USER_TYPE");

        UserType modifiedUserType = new UserTypeUpdateUseCase(existingUserType, dtoUserType).getBuiltedUserType();
        assertEquals(ID, modifiedUserType.getId());
        assertEquals("NEW_USER_TYPE", modifiedUserType.getName());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao modificar um tipo de usuário padrão(administrador, dono de restaurante e cliente)")
    void shouldNotUpdateDefaultUserType(){
        UserType existingUserType = createExistingUserTypeMock("OWNER");

        assertThrows(EntityCannotBeDeletedException.class, () -> new UserTypeUpdateUseCase(existingUserType, dtoUserType));
    }

    private UserType createExistingUserTypeMock(String name){
        return new UserType(ID, name);
    }
}