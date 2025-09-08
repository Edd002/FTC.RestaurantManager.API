package com.fiap.tech.challenge.domain.reservation.usecase;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import lombok.NonNull;

public class ReservationCreateUseCase {

    private final Reservation reservation;

    public ReservationCreateUseCase(@NonNull User loggedUser, @NonNull Restaurant restaurant, @NonNull ReservationPostRequestDTO reservationPostRequestDTO) {
        this.reservation = buildReservation(loggedUser, restaurant, reservationPostRequestDTO);
    }

    private Reservation buildReservation(User loggedUser, Restaurant restaurant, ReservationPostRequestDTO reservationPostRequestDTO) {
        return new Reservation(
                ReservationBookingStatusEnum.REQUESTED,
                ReservationBookingTimeEnum.valueOf(reservationPostRequestDTO.getBookingTime()),
                reservationPostRequestDTO.getBookingDate(),
                reservationPostRequestDTO.getBookingQuantity(),
                restaurant,
                loggedUser
        );
    }

    public Reservation getBuiltedReservation() {
        return this.reservation;
    }
}