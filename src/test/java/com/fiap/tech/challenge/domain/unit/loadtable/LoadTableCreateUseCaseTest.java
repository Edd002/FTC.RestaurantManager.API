package com.fiap.tech.challenge.domain.unit.loadtable;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.domain.loadtable.usecase.LoadTableCreateUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadTableCreateUseCaseTest {

    @Test
    @DisplayName("Teste de sucesso - Deve retornar a mesma instância de LoadTable quando não for nulo")
    void shouldReturnSameLoadTableWhenNotNull() {
        LoadTable originalLoadTable = new LoadTable("Restaurant");

        LoadTableCreateUseCase useCase = new LoadTableCreateUseCase(originalLoadTable, "Menu");
        LoadTable result = useCase.getBuiltedLoadTable();

        assertSame(originalLoadTable, result, "Deve retornar a mesma instância de LoadTable fornecida");
        assertEquals("Restaurant", result.getEntityName(), "Deve manter o entity original");
    }

    @Test
    @DisplayName("Teste de sucesso - Deve criar novo LoadTable com entityName quando parâmetro for nulo")
    void shouldCreateNewLoadTableWhenNullPassed() {
        String expectedEntity = "User";

        LoadTableCreateUseCase useCase = new LoadTableCreateUseCase(null, expectedEntity);
        LoadTable result = useCase.getBuiltedLoadTable();

        assertNotNull(result, "Deve retornar uma nova instância de LoadTable");
        assertEquals(expectedEntity, result.getEntityName(), "O novo LoadTable deve conter o entityName fornecido");
    }
}
