package com.fiap.tech.challenge.domain.reservation.enumerated;

import lombok.Getter;

@Getter
public enum ReservationStatusEnum {

    REQUESTED("Reservation status for requested"),
    ACCEPTED("Reservation status for accepted"),
    REJECTED("Reservation status for rejected");

    private final String description;

    ReservationStatusEnum(String description) {
        this.description = description;
    }
}