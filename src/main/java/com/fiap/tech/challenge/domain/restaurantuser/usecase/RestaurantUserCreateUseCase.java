package com.fiap.tech.challenge.domain.restaurantuser.usecase;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.entity.User;
import lombok.NonNull;

public class RestaurantUserCreateUseCase {

    private final RestaurantUser restaurantUser;

    public RestaurantUserCreateUseCase(@NonNull User loggedUser, @NonNull Restaurant restaurant) {
        this.restaurantUser = buildRestaurantUser(loggedUser, restaurant);
    }

    private RestaurantUser buildRestaurantUser(User loggedUser, Restaurant restaurant) {
        return new RestaurantUser(restaurant, loggedUser);
    }

    public RestaurantUser getBuiltedURestaurantUser() {
        return this.restaurantUser;
    }
}