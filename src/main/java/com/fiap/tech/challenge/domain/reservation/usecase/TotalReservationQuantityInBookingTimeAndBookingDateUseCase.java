package com.fiap.tech.challenge.domain.reservation.usecase;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import lombok.NonNull;

import java.util.List;

public class TotalReservationQuantityInBookingTimeAndBookingDateUseCase {

    private final Long totalReservationQuantityInBookingTimeAndBookingDate;

    public TotalReservationQuantityInBookingTimeAndBookingDateUseCase(@NonNull List<Reservation> restaurantReservations, @NonNull ReservationPostRequestDTO reservationPostRequestDTO) {
        this.totalReservationQuantityInBookingTimeAndBookingDate = buildTotalReservationQuantityInBookingTimeAndBookingDateUseCase(restaurantReservations, reservationPostRequestDTO);
    }

    private Long buildTotalReservationQuantityInBookingTimeAndBookingDateUseCase(List<Reservation> restaurantReservations, ReservationPostRequestDTO reservationPostRequestDTO) {
        return restaurantReservations.stream()
                .filter(restaurantReservation -> !restaurantReservation.getBookingStatus().equals(ReservationBookingStatusEnum.REJECTED)
                        && (restaurantReservation.getBookingTime().equals(ReservationBookingTimeEnum.valueOf(reservationPostRequestDTO.getBookingTime()))
                        && restaurantReservation.getBookingDate().equals(reservationPostRequestDTO.getBookingDate())))
                .mapToLong(Reservation::getBookingQuantity)
                .sum();
    }

    public Long getBuiltedTotalReservationQuantityInBookingTimeAndBookingDate() {
        return this.totalReservationQuantityInBookingTimeAndBookingDate;
    }
}