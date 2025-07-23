package com.fiap.tech.challenge.domain.unit.restaurantuser;

import com.fiap.tech.challenge.domain.factory.RestaurantUserTestFactory;
import com.fiap.tech.challenge.domain.factory.UserTestFactory;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCheckForDeleteUseCase;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantUserCheckForDeleteUseCaseTest {

    @Test
    void shouldThrowEntityCannotBeDeletedExceptionIfUserIsOwner() {
        EntityCannotBeDeletedException exception =  assertThrows(EntityCannotBeDeletedException.class, () -> new RestaurantUserCheckForDeleteUseCase(UserTestFactory.loadEntityOwner(), List.of(RestaurantUserTestFactory.loadEntityRestaurantUser())));

        assertEquals("Não foi possível realizar a exclusão pois deve existir pelo menos uma associação de usuário dono de restaurante com o restaurante.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Usuário for nulo")
    void shouldThrowExceptionIfUserIsNull() {
        assertThrows(NullPointerException.class, () -> new RestaurantUserCheckForDeleteUseCase(null, List.of(RestaurantUserTestFactory.loadEntityRestaurantUser())));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se lista de RestaurantUser for nulo")
    void shouldThrowExceptionIfRestaurantIsNull() {
        assertThrows(NullPointerException.class, () -> new RestaurantUserCheckForDeleteUseCase(UserTestFactory.loadEntityOwner(), null));
    }
}