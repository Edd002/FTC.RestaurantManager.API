package com.fiap.tech.challenge.domain.unit.restaurantuser;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCreateUseCase;
import com.fiap.tech.challenge.domain.user.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RestaurantUserCreateUseCaseTest {
    @Mock
    private Restaurant restaurant;
    @Mock
    private User loggedUser;

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
    @DisplayName("Teste de sucesso - Deve criar o usuário do restaurante")
    void shouldCreateRestaurantUser(){
        RestaurantUser restaurantUser = new RestaurantUserCreateUseCase(loggedUser, restaurant).getBuiltedURestaurantUser();

        assertNotNull(restaurantUser);
    }
}