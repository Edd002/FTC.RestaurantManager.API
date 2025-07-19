package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.menu.entity.Menu;

import java.util.List;

public class MenuTestFactory {

    public static Menu loadEntityMenu() {
        return new Menu(1L, List.of());
    }
}
