package com.fiap.tech.challenge.domain.unit.jwt;

import com.fiap.tech.challenge.domain.factory.JwtTestFactory;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtIsActiveUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtIsActiveUseCaseTest {

    private JwtTestFactory jwtTestFactory;

    @BeforeEach
    void setUp() {
        jwtTestFactory = new JwtTestFactory();
    }

    @Test
    @DisplayName("Teste de sucesso - Deve retornar true se o JWT estiver ativo")
    void shouldReturnTrueIfJwtIsActive() {
        Jwt jwt = jwtTestFactory.loadDefaultJwt();

        JwtIsActiveUseCase useCase = new JwtIsActiveUseCase(jwt, 300000);
        boolean result = useCase.isActive();

        assertTrue(result);
    }

    @Test
    @DisplayName("Teste de falha - Deve retornar false se o JWT estiver expirado")
    void shouldReturnFalseIfJwtIsExpired() {
        Jwt jwt = jwtTestFactory.loadExpiredTokenJwt();

        JwtIsActiveUseCase useCase = new JwtIsActiveUseCase(jwt, 1000);
        boolean result = useCase.isActive();

        assertFalse(result);
    }

    @Test
    @DisplayName("Teste de falha - Deve retornar false se o JWT for nulo")
    void shouldReturnFalseIfJwtIsNull() {
        JwtIsActiveUseCase useCase = new JwtIsActiveUseCase(null, 1000);
        boolean result = useCase.isActive();

        assertFalse(result);
    }
}