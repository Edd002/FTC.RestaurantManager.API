package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.util.JsonUtil;

import java.math.BigDecimal;


public class MenuItemTestFactory {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";

    public static MenuItemPostRequestDTO loadValidMenuItemDTO() {
        return JsonUtil.objectFromJson(
                "menuItemPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }

    public static MenuItemPutRequestDTO loadValidMenuItemPutDTO() {
        return JsonUtil.objectFromJson(
                "menuItemPutRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPutRequestDTO.class
        );
    }

    public static MenuItemPostRequestDTO loadInvalidMenuItemDTO() {
        return JsonUtil.objectFromJson(
                "menuItemInvalidPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }

    public static MenuItemPutRequestDTO loadInvalidMenuItemPutDTO() {
        return JsonUtil.objectFromJson(
                "menuItemInvalidPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPutRequestDTO.class
        );
    }

    public static MenuItem loadEntityMenuItem() {
        return new MenuItem(1L,
                "Espaguete",
                "Espaguete à bolonhesa.",
                BigDecimal.valueOf(19.99),
                true,
                "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808",
                MenuTestFactory.loadEntityMenu());
    }
}
