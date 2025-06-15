package com.fiap.tech.challenge.domain.restaurant;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRestaurantRepository extends IBaseRepository<Restaurant> {
}