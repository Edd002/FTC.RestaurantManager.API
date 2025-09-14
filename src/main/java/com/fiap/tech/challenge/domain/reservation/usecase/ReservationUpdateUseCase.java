package com.fiap.tech.challenge.domain.reservation.usecase;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import lombok.NonNull;

public class ReservationUpdateUseCase {

    private final Reservation reservation;

    public ReservationUpdateUseCase(@NonNull Reservation existingReservation, @NonNull ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO) {
        this.reservation = rebuildReservation(existingReservation, reservationUpdateStatusPatchRequestDTO);
    }

    public ReservationUpdateUseCase(@NonNull Reservation existingReservation, @NonNull ReservationBookingStatusEnum status) {
        this.reservation = rebuildReservation(existingReservation, status);
    }

    private Reservation rebuildReservation(Reservation existingReservation, ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO) {
        return existingReservation.rebuild(
                ReservationBookingStatusEnum.valueOf(reservationUpdateStatusPatchRequestDTO.getStatus())
        );
    }

    private Reservation rebuildReservation(Reservation existingReservation, ReservationBookingStatusEnum status) {
        return existingReservation.rebuild(status);
    }

    public Reservation getRebuiltedReservation() {
        return this.reservation;
    }
}