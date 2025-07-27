package com.fiap.tech.challenge.domain.unit.usertype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fiap.tech.challenge.domain.factory.UserTypeFactory;
import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeCreateUseCase;

public class UserTypeCreateUseCaseTest {

    @Test
    @DisplayName("Teste de sucesso - Deve criar o user type corretamente")
    void shouldCreateUserTypeSuccessfully(){
        UserTypePostRequestDTO dtoUserType = UserTypeFactory.loadValidChefPostRequestDTO();

        UserType created = new UserTypeCreateUseCase(dtoUserType).getBuiltedUserType();

        assertEquals("CHEF", created.getName());
    }

}