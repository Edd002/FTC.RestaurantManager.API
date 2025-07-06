package com.fiap.tech.challenge.domain.menuitem.usecase;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import lombok.NonNull;

public class MenuItemCreateUseCase {

    private final MenuItem menuItem;

    public MenuItemCreateUseCase(@NonNull Restaurant restaurant, @NonNull MenuItemPostRequestDTO menuItemPostRequestDTO) {
        this.menuItem = buildMenuItem(restaurant, menuItemPostRequestDTO);
    }

    private MenuItem buildMenuItem(Restaurant restaurant, MenuItemPostRequestDTO menuItemPostRequestDTO) {
        return new MenuItem(
                menuItemPostRequestDTO.getName(),
                menuItemPostRequestDTO.getDescription(),
                menuItemPostRequestDTO.getPrice(),
                menuItemPostRequestDTO.getAvailability(),
                menuItemPostRequestDTO.getPhotoUrl(),
                restaurant.getMenu()
        );
    }

    public MenuItem getBuiltedMenuItem() {
        return this.menuItem;
    }
}