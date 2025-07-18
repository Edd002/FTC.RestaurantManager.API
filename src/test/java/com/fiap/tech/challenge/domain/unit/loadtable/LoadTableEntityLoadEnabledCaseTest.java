package com.fiap.tech.challenge.domain.unit.loadtable;

import com.fiap.tech.challenge.domain.loadtable.entity.LoadTable;
import com.fiap.tech.challenge.domain.loadtable.usecase.LoadTableEntityLoadEnabledCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadTableEntityLoadEnabledCaseTest {

    @Test
    @DisplayName("Teste de sucesso - Deve retornar true quando LoadTable for null")
    void shouldReturnTrueWhenLoadTableIsNull() {
        LoadTableEntityLoadEnabledCase useCase = new LoadTableEntityLoadEnabledCase(null);

        assertTrue(useCase.isEntityLoadEnabled());
    }

    @Test
    @DisplayName("Teste de sucesso - Deve retornar false quando LoadTable.entityLoadEnabled for false")
    void shouldReturnFalseWhenLoadTableEntityLoadEnabledIsFalse() {
        LoadTable loadTable = new LoadTable("Restaurant");

        LoadTableEntityLoadEnabledCase useCase = new LoadTableEntityLoadEnabledCase(loadTable);

        assertFalse(useCase.isEntityLoadEnabled());
    }
}
