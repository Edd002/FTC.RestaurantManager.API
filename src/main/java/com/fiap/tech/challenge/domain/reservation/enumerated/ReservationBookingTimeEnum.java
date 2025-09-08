package com.fiap.tech.challenge.domain.reservation.enumerated;

import lombok.Getter;

@Getter
public enum ReservationBookingTimeEnum {

    BREAKFAST("Breakfase iime"),
    LUNCH("Lunch time"),
    DINNER("Dinner time");

    private final String description;

    ReservationBookingTimeEnum(String description) {
        this.description = description;
    }
}