package com.fiap.tech.challenge.domain.unit.restaurantuser;

import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.factory.UserTestFactory;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.restaurantuser.usecase.RestaurantUserCreateUseCase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum.QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestaurantUserCreateUseCaseTest {

    @DisplayName("Teste de sucesso - Deve conseguir criar uma associação entre usuário e restaurante com sucesso")
    @Test
    void shouldCreateRestaurantUserUseCaseSuccess() {
        RestaurantUserCreateUseCase restaurantUserCreateUseCase = new RestaurantUserCreateUseCase(UserTestFactory.loadEntityOwner(), RestaurantTestFactory.loadEntityRestaurant());

        RestaurantUser restaurantUser = restaurantUserCreateUseCase.getBuiltedURestaurantUser();

        assertThat(restaurantUser).isNotNull();
        assertThat(restaurantUser.getId()).isNull();
        assertThat(restaurantUser.getUser().getName()).isEqualTo("Manu");
        assertThat(restaurantUser.getUser().getEmail()).isEqualTo("manu@email.com");
        assertThat(restaurantUser.getRestaurant().getName()).isEqualTo("Old Restaurant");
        assertThat(restaurantUser.getRestaurant().getType()).isEqualTo(QUICK_SERVICE_RESTAURANTS_OR_FAST_FOOD);
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Usuário for nulo")
    void shouldThrowExceptionIfUserIsNull() {
        assertThrows(NullPointerException.class, () -> new RestaurantUserCreateUseCase(null, RestaurantTestFactory.loadEntityRestaurant()));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Restaurante for nulo")
    void shouldThrowExceptionIfRestaurantIsNull() {
        assertThrows(NullPointerException.class, () -> new RestaurantUserCreateUseCase(UserTestFactory.loadEntityOwner(), null));
    }
}