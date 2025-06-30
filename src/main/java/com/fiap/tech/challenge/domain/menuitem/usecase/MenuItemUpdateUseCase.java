package com.fiap.tech.challenge.domain.menuitem.usecase;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public class MenuItemUpdateUseCase {

    private final MenuItem menuItem;

    public MenuItemUpdateUseCase(@NonNull MenuItem existingMenuItem, @NonNull Restaurant restaurant, @NonNull MenuItemPutRequestDTO menuItemPutRequestDTO) {
        this.menuItem = buildMenuItem(existingMenuItem, restaurant, menuItemPutRequestDTO);
    }

    private MenuItem buildMenuItem(MenuItem existingMenuItem, Restaurant restaurant, MenuItemPutRequestDTO menuItemPutRequestDTO) {
        return new MenuItem(
                existingMenuItem.getId(),
                menuItemPutRequestDTO.getName(),
                menuItemPutRequestDTO.getDescription(),
                menuItemPutRequestDTO.getPrice(),
                menuItemPutRequestDTO.getAvailability(),
                menuItemPutRequestDTO.getPhotoUrl(),
                restaurant.getMenu()
        );
    }

    public MenuItem getBuiltedMenuItem() {
        return this.menuItem;
    }
}