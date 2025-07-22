package com.fiap.tech.challenge.domain.factory;

import java.util.Date;

import com.fiap.tech.challenge.domain.menu.entity.Menu;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;

public class RestaurantTestFactory {

    public static Menu loadEmptyMenu() {
        return new Menu();
    }

    public static Restaurant loadEntityRestaurant() {
        return new Restaurant(
                "Restaurante Teste",
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                RestaurantTypeEnum.CAFETERIA,
                loadEmptyMenu(),
                AddressFactory.loadAddressEntity()
        );
    }
}
