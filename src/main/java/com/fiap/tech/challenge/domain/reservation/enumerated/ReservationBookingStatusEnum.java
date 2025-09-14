package com.fiap.tech.challenge.domain.reservation.enumerated;

import com.fiap.tech.challenge.global.util.ValidationUtil;
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

    public static boolean isCanceled(ReservationBookingStatusEnum status) {
        return ValidationUtil.isNotNull(status) && (status.equals(CANCELED));
    }
}