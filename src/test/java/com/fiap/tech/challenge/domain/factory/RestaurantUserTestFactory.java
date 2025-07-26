package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;

public class RestaurantUserTestFactory {

    public static RestaurantUser loadEntityRestaurantUser() {
        return new RestaurantUser(RestaurantTestFactory.loadEntityRestaurant(), UserTestFactory.loadEntityOwner());
    }

}
