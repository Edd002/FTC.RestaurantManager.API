package com.fiap.tech.challenge.domain.unit.jwt;

import com.fiap.tech.challenge.domain.factory.JwtTestFactory;
import com.fiap.tech.challenge.domain.jwt.entity.Jwt;
import com.fiap.tech.challenge.domain.jwt.usecase.JwtRefreshByBearerTokenUserCase;
import com.fiap.tech.challenge.global.exception.InvalidBearerTokenHttpException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JwtRefreshByBearerTokenUserCaseTest {

    private JwtTestFactory jwtTestFactory;

    @BeforeEach
    void setUp() {
        jwtTestFactory = new JwtTestFactory();
    }
    @Test
    @DisplayName("Teste de sucesso - Deve atualizar o campo updatedIn do JWT se estiver ativo")
    void shouldRefreshJwtIfActive() {

        Jwt jwt = jwtTestFactory.loadDefaultJwt();
        var dateUpdated = jwt.getUpdatedIn();

        JwtRefreshByBearerTokenUserCase useCase = new JwtRefreshByBearerTokenUserCase(jwt, 300000);
        Jwt result = useCase.getBuiltedJwt();

        assertNotNull(result);
        assertSame(jwt, result);
        assertNotEquals(dateUpdated, result.getUpdatedIn());

    }

    @Test
    @DisplayName("Teste de falha - Deve lançar exceção se o JWT estiver expirado")
    void shouldThrowExceptionIfJwtIsExpired() {

        Jwt jwt = jwtTestFactory.loadExpiredTokenJwt();

        assertThrows(InvalidBearerTokenHttpException.class,
                () -> new JwtRefreshByBearerTokenUserCase(jwt, 1000));
    }
}