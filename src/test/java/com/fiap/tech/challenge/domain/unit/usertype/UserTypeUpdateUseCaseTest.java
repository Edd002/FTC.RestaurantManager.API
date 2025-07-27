package com.fiap.tech.challenge.domain.unit.usertype;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePutRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeUpdateUseCase;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;

public class UserTypeUpdateUseCaseTest {

    private UserTypePutRequestDTO dtoUserType;

    @Test
    @DisplayName("Teste de sucesso - Deve atualizar o tipo de usuário com um novo nome")
    void shouldUpdateUserTypeWithNewName(){
        UserType existingUserType = UserTypeFactory.loadEntityUserTypeOther();
        dtoUserType = UserTypeFactory.loadValidEmployeePutRequestDTO();

        UserType modifiedUserType = new UserTypeUpdateUseCase(existingUserType, dtoUserType).getRebuiltedUserType();
        assertEquals("EMPLOYEE", modifiedUserType.getName());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção ao modificar um tipo de usuário padrão(administrador, dono de restaurante e cliente)")
    void shouldNotUpdateDefaultUserType(){
        UserType existingUserType = UserTypeFactory.loadEntityUserTypeOwner();
        dtoUserType = UserTypeFactory.loadValidEmployeePutRequestDTO();

        assertThrows(EntityCannotBeDeletedException.class, () -> new UserTypeUpdateUseCase(existingUserType, dtoUserType));
    }
}