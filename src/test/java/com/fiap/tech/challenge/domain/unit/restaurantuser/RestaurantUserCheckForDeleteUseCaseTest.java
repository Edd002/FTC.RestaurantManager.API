package com.fiap.tech.challenge.domain.unit.restaurantuser;

import com.fiap.tech.challenge.domain.factory.RestaurantUserTestFactory;
import com.fiap.tech.challenge.domain.factory.UserTestFactory;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCheckForDeleteUseCase;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.exception.EntityCannotBeDeletedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RestaurantUserCheckForDeleteUseCaseTest {

    @DisplayName("Teste de sucesso - Deve conseguir deletar uma associação entre usuário e restaurante com sucesso")
    @Test
    void shouldDeleteRestaurantUserUseCaseSuccess() {
        RestaurantUserCheckForDeleteUseCase restaurantUserCheckForDeleteUseCase = new RestaurantUserCheckForDeleteUseCase(UserTestFactory.loadEntityClient(), List.of(RestaurantUserTestFactory.loadEntityRestaurantUser()));

        boolean isAllowedToDelete = restaurantUserCheckForDeleteUseCase.isAllowedToDelete();

        assertThat(isAllowedToDelete).isTrue();
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar EntityCannotBeDeletedException se o usuário é admin")
    void shouldThrowEntityCannotBeDeletedExceptionIfUserIsAdmin() {
        User admin = mock(User.class);
        UserType adminType = mock(UserType.class);
        when(admin.getType()).thenReturn(adminType);
        when(admin.getType().getName()).thenReturn("ADMIN");

        EntityCannotBeDeletedException exception =  assertThrows(EntityCannotBeDeletedException.class, () -> new RestaurantUserCheckForDeleteUseCase(admin, List.of(RestaurantUserTestFactory.loadEntityRestaurantUser())));

        assertEquals("Usuários administradores não podem ser excluídos.", exception.getMessage());
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar EntityCannotBeDeletedException se o dono de restaurante só tiver uma associação com o restaurante")
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