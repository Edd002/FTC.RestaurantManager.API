package com.fiap.tech.challenge.domain.menu.usecase;

import com.fiap.tech.challenge.domain.menu.dto.MenuBatchPutRequestDTO;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.enumerated.UserRoleEnum;
import com.fiap.tech.challenge.global.exception.AuthorizationException;
import lombok.NonNull;

public class MenuCreateUseCase {

    private final Menu menu;

    public MenuCreateUseCase(@NonNull User loggedUser, @NonNull Restaurant restaurant, @NonNull MenuBatchPutRequestDTO menuBatchPostRequestDTO) {
        if (!loggedUser.getRole().equals(UserRoleEnum.OWNER)) {
            throw new AuthorizationException("O usuário deve ser do tipo DONO (OWNER) para cadastrar um menu.");
        }
        this.menu = buildMenu(restaurant, menuBatchPostRequestDTO);
    }

    private Menu buildMenu(Restaurant restaurant, MenuBatchPutRequestDTO menuBatchPostRequestDTO) {
        return new Menu(
                restaurant.getMenu().getId(),
                null/*menuBatchPostRequestDTO.getMenuItems()*/
        );
    }

    public Menu getBuiltedMenu() {
        return this.menu;
    }
}