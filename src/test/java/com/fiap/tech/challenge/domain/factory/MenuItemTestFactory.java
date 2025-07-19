package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.global.util.JsonUtil;


public class MenuItemTestFactory {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";

    public static MenuItemPostRequestDTO loadValidMenuItemDTO() {
        return JsonUtil.objectFromJson(
                "menuItemPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }

    public static MenuItemPostRequestDTO loadInvalidMenuItemDTO() {
        return JsonUtil.objectFromJson(
                "menuItemInvalidPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }
}
