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

    public MenuItemUpdateUseCase(@NonNull User loggedUser, @NonNull MenuItem existingMenuItem, @NonNull Restaurant restaurant, @NonNull MenuItemPutRequestDTO menuItemPutRequestDTO) {
        if (!loggedUser.getRole().equals(UserRoleEnum.OWNER)) {
            throw new AuthorizationException("O usuário deve ser do tipo DONO (OWNER) para atualizar um item de menu.");
        }
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