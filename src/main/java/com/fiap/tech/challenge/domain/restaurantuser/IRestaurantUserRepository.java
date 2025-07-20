package com.fiap.tech.challenge.domain.restaurantuser;

import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRestaurantUserRepository extends IBaseRepository<RestaurantUser> {

    List<RestaurantUser> findByRestaurant(Restaurant restaurant);

    Optional<RestaurantUser> findByRestaurantHashIdAndUser(String hashId, User user);

    Optional<RestaurantUser> findByHashIdAndUser(String hashId, User user);

    Optional<RestaurantUser> findByRestaurantAndUser(Restaurant restaurant, User user);
}