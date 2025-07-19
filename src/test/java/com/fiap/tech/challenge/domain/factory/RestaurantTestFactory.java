package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.address.entity.Address;
import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;

import java.util.Date;

public class RestaurantTestFactory {

    public static Menu createMenu() {
        return new Menu();
    }

    public static Address createAddress() {
        return new Address(1L);
    }

    public static Restaurant createRestaurant() {
        return new Restaurant(
                1L,
                "Restaurante Teste",
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                RestaurantTypeEnum.CAFETERIA,
                createMenu(),
                createAddress()
        );
    }
}
