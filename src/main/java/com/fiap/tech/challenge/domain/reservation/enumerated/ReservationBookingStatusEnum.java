package com.fiap.tech.challenge.domain.reservation.enumerated;

import lombok.Getter;

@Getter
public enum ReservationBookingStatusEnum {

    REQUESTED("Reservation status for requested"),
    ACCEPTED("Reservation status for accepted"),
    REJECTED("Reservation status for rejected"),
    CANCELED("Order status canceled");

    private final String description;

    ReservationBookingStatusEnum(String description) {
        this.description = description;
    }
}