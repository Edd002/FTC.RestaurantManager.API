package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPostRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.util.JsonUtil;

import java.math.BigDecimal;


public class MenuItemTestFactory {

    private static final String PATH_RESOURCE_MENU_ITEM = "/mock/menuitem/menuitem.json";

    public static MenuItemPostRequestDTO loadValidPostRequestDTO() {
        return JsonUtil.objectFromJson(
                "menuItemPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }

    public static MenuItemPutRequestDTO loadValidPutRequestDTO() {
        return JsonUtil.objectFromJson(
                "menuItemPutRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPutRequestDTO.class
        );
    }

    public static MenuItemPostRequestDTO loadInvalidPostRequestDTO() {
        return JsonUtil.objectFromJson(
                "menuItemInvalidPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPostRequestDTO.class
        );
    }

    public static MenuItemPutRequestDTO loadInvalidPutRequestDTO() {
        return JsonUtil.objectFromJson(
                "menuItemInvalidPostRequestDTO",
                PATH_RESOURCE_MENU_ITEM,
                MenuItemPutRequestDTO.class
        );
    }

    public static MenuItem loadEntityMenuItem() {
        return new MenuItem(
                "Espaguete",
                "Espaguete à bolonhesa.",
                BigDecimal.valueOf(19.99),
                true,
                "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808",
                MenuTestFactory.loadEntityMenu());
    }
}
