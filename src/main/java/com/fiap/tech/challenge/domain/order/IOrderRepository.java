package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IOrderRepository extends IBaseRepository<Order> {

    Optional<Order> findByHashIdAndUser(String hashId, User user);

    Optional<Order> findByHashIdAndRestaurantAndUserHashId(String hashId, Restaurant restaurant, String userHashId);
}