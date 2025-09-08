package com.fiap.tech.challenge.domain.reservation.usecase;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPutRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import lombok.NonNull;

public class ReservationUpdateUseCase {

    private final Reservation reservation;

    public ReservationUpdateUseCase(@NonNull Order existingOrder, @NonNull ReservationPutRequestDTO reservationPutRequestDTO) {
        this.reservation = rebuildReservation(existingOrder, reservationPutRequestDTO);
    }

    private Reservation rebuildReservation(Order existingOrder, ReservationPutRequestDTO reservationPutRequestDTO) {
        return null;
    }

    public Reservation getRebuiltedReservation() {
        return this.reservation;
    }
}