package com.fiap.tech.challenge.domain.reservation;

import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IReservationRepository extends IBaseRepository<Reservation> {

    Optional<Reservation> findByHashIdAndUser(String hashId, User user);

    Optional<Reservation> findByHashIdAndRestaurantAndUserHashId(String hashId, Restaurant restaurant, String userHashId);
}