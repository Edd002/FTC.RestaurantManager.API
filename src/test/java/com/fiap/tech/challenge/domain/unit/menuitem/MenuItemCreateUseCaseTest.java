package com.fiap.tech.challenge.domain.unit.menuitem;

import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemCreateUseCase;
import com.fiap.tech.challenge.domain.factory.MenuItemTestFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class MenuItemCreateUseCaseTest {

    @DisplayName("Teste de sucesso - Deve conseguir criar um item no menu com sucesso")
    @Test
    void shouldCreateMenuItemSuccessfully() {
        MenuItemPostRequestDTO menuItemPostRequestDTO = MenuItemTestFactory.loadValidMenuItemDTO();
        MenuItemCreateUseCase useCase = new MenuItemCreateUseCase(RestaurantTestFactory.createRestaurant(), menuItemPostRequestDTO);

        MenuItem menuItem = useCase.getBuiltedMenuItem();

        assertThat(menuItem).isNotNull();
        assertThat(menuItem.getId()).isNull();
        assertThat(menuItem.getName()).isEqualTo(menuItemPostRequestDTO.getName());
        assertThat(menuItem.getDescription()).isEqualTo(menuItemPostRequestDTO.getDescription());
        assertThat(menuItem.getPrice()).isEqualTo(menuItemPostRequestDTO.getPrice());
        assertThat(menuItem.getAvailability()).isEqualTo(menuItemPostRequestDTO.getAvailability());
        assertThat(menuItem.getPhotoUrl()).isEqualTo(menuItemPostRequestDTO.getPhotoUrl());
    }

    @DisplayName("Teste de falha - Não deve conseguir criar um item no menu")
    @Test
    void shouldThrowExceptionWhenRequiredFieldsAreNull() {
        MenuItemPostRequestDTO menuItemPostRequestDTO = MenuItemTestFactory.loadInvalidMenuItemDTO();

        assertThrows(NullPointerException.class, () -> {
            new MenuItemCreateUseCase(RestaurantTestFactory.createRestaurant(), menuItemPostRequestDTO);
        });
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Restaurante for nulo")
    void shouldThrowExceptionIfRestaurantIsNull() {
        MenuItemPostRequestDTO dto = MenuItemTestFactory.loadValidMenuItemDTO();

        assertThrows(NullPointerException.class, () -> new MenuItemCreateUseCase(null, dto));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se MenuItemPostRequestDTO for nulo")
    void shouldThrowExceptionIfDTOIsNull() {
        assertThrows(NullPointerException.class, () -> new MenuItemCreateUseCase(RestaurantTestFactory.createRestaurant(), null));
    }
}