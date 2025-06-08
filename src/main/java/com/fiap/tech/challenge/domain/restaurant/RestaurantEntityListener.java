package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class RestaurantEntityListener {

    @PostLoad
    public void postLoad(Restaurant restaurantEntity) {
        restaurantEntity.saveState(SerializationUtils.clone(restaurantEntity));
    }
}