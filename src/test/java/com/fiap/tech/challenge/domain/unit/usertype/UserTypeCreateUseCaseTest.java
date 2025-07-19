package com.fiap.tech.challenge.domain.unit.usertype;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypePostRequestDTO;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.domain.usertype.usecase.UserTypeCreateUseCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserTypeCreateUseCaseTest {

    @Mock
    private UserTypePostRequestDTO dtoUserType;

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
    @DisplayName("Teste de sucesso - Deve criar o user type corretamente")
    void shouldCreateUserTypeSuccessfully(){
        when(dtoUserType.getName()).thenReturn("OWNER");

        UserType created = new UserTypeCreateUseCase(dtoUserType).getBuiltedUserType();

        assertEquals("OWNER", created.getName());
    }

}