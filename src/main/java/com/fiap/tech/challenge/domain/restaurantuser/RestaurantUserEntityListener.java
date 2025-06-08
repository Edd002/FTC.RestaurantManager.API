package com.fiap.tech.challenge.domain.restaurantuser;

import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class RestaurantUserEntityListener {

    @PostLoad
    public void postLoad(RestaurantUser restaurantUserEntity) {
        restaurantUserEntity.saveState(SerializationUtils.clone(restaurantUserEntity));
    }
}