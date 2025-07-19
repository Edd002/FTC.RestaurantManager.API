package com.fiap.tech.challenge.domain.unit.menuitem;

import com.fiap.tech.challenge.domain.factory.MenuItemTestFactory;
import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.menuitem.usecase.MenuItemUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class MenuItemUpdateUseCaseTest {

    private static Restaurant restaurant;
    private static MenuItemPutRequestDTO menuItemPutRequestDTO;
    private static MenuItem existingMenuItem;
    private static MenuItemPutRequestDTO invalidMenuItemPutRequestDTO;

    @BeforeAll
    static void init() {
        restaurant = RestaurantTestFactory.loadEntityRestaurant();
        menuItemPutRequestDTO = MenuItemTestFactory.loadValidMenuItemPutDTO();
        existingMenuItem = MenuItemTestFactory.loadEntityMenuItem();
        invalidMenuItemPutRequestDTO = MenuItemTestFactory.loadInvalidMenuItemPutDTO();
    }


    @DisplayName("Teste de sucesso - Deve conseguir atualizar um item no menu com sucesso")
    @Test
    void shouldBuildMenuItemWithUpdatedData() {
        MenuItemUpdateUseCase useCase = new MenuItemUpdateUseCase(existingMenuItem, restaurant, menuItemPutRequestDTO);

        MenuItem updatedMenuItem = useCase.getBuiltedMenuItem();

        assertNotNull(updatedMenuItem);
        assertEquals(existingMenuItem.getId(), updatedMenuItem.getId());
        assertEquals("UPDATED: Espaguete", updatedMenuItem.getName());
        assertEquals("UPDATED: Espaguete à bolonhesa.", updatedMenuItem.getDescription());
        assertEquals(BigDecimal.valueOf(29.99), updatedMenuItem.getPrice());
        assertTrue(updatedMenuItem.getAvailability());
        assertEquals(existingMenuItem.getPhotoUrl(), updatedMenuItem.getPhotoUrl());
    }

    @DisplayName("Teste de falha - Não deve conseguir atualizar um item no menu")
    @Test
    void shouldThrowExceptionWhenRequiredFieldsAreNull() {
        assertThrows(NullPointerException.class, () -> {
            new MenuItemUpdateUseCase(existingMenuItem, restaurant, invalidMenuItemPutRequestDTO);
        });
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Menu Item for nulo")
    void shouldThrowExceptionIfExistingMenuItemIsNull() {
        assertThrows(NullPointerException.class, () -> new MenuItemUpdateUseCase(null, restaurant, menuItemPutRequestDTO));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se Restaurante for nulo")
    void shouldThrowExceptionIfRestaurantIsNull() {
        assertThrows(NullPointerException.class, () -> new MenuItemUpdateUseCase(existingMenuItem, null, menuItemPutRequestDTO));
    }

    @Test
    @DisplayName("Teste de falha - Deve lançar NullPointerException se MenuItemPutRequestDTO for nulo")
    void shouldThrowExceptionIfDTOIsNull() {
        assertThrows(NullPointerException.class, () -> new MenuItemUpdateUseCase(existingMenuItem, restaurant, null));
    }
}