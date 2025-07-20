package com.fiap.tech.challenge.domain.menuitem.usecase;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import lombok.NonNull;

public class MenuItemUpdateUseCase {

    private final MenuItem menuItem;

    public MenuItemUpdateUseCase(@NonNull MenuItem existingMenuItem, @NonNull Restaurant restaurant, @NonNull MenuItemPutRequestDTO menuItemPutRequestDTO) {
        this.menuItem = rebuildMenuItem(existingMenuItem, restaurant, menuItemPutRequestDTO);
    }

    private MenuItem rebuildMenuItem(MenuItem existingMenuItem, Restaurant restaurant, MenuItemPutRequestDTO menuItemPutRequestDTO) {
        return existingMenuItem.rebuild(
                menuItemPutRequestDTO.getName(),
                menuItemPutRequestDTO.getDescription(),
                menuItemPutRequestDTO.getPrice(),
                menuItemPutRequestDTO.getAvailability(),
                menuItemPutRequestDTO.getPhotoUrl(),
                restaurant.getMenu()
        );
    }

    public MenuItem getRebuiltedMenuItem() {
        return this.menuItem;
    }
}