package com.fiap.tech.challenge.domain.unit.reservation;

import com.fiap.tech.challenge.domain.factory.ReservationTestFactory;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.domain.reservation.usecase.ReservationUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.global.exception.ReservationUpdateException;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.util.DateTimeUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ReservationUpdateUseCaseTest {

    @DisplayName("Teste de sucesso - Deve conseguir atualizar uma Reserva no Restaurante com sucesso")
    @Test
    void shouldUpdateReservationSuccessfully() {
        ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO = ReservationTestFactory.loadValidUpdateRequestDTO();
        Reservation reservation = mock(Reservation.class);
        when(reservation.rebuild(any(ReservationBookingStatusEnum.class))).thenReturn(reservation);

        ReservationUpdateUseCase reservationUpdateUseCase = new ReservationUpdateUseCase(reservation, reservationUpdateStatusPatchRequestDTO);
        Reservation reservationUpdated = reservationUpdateUseCase.getRebuiltedReservation();

        assertThat(reservationUpdated).isEqualTo(reservation);
    }

    @DisplayName("Teste de falha - Não deve conseguir atualizar uma Reserva no Restaurante com data anterior a hoje")
    @Test
    void shouldNotUpdateReservationSuccessfullyWithInvalidDate() {
        try (MockedStatic<DateTimeUtil> mockedDateUtil = mockStatic(DateTimeUtil.class)) {
            LocalDate fakeToday = LocalDate.of(2024, 1, 1);
            Date fakeNow = Date.from(fakeToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            mockedDateUtil.when(DateTimeUtil::nowTruncate).thenReturn(fakeNow);

            ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO = ReservationTestFactory.loadValidUpdateRequestDTO();
            Restaurant restaurant = mock(Restaurant.class);
            User user = mock(User.class);
            when(restaurant.getLimitReservations(ReservationBookingTimeEnum.LUNCH)).thenReturn(100);
            LocalDate reservationDate = LocalDate.of(2024, 1, 5);
            Date bookingDate = Date.from(reservationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

            Reservation reservation = new Reservation(
                    ReservationBookingStatusEnum.ACCEPTED,
                    ReservationBookingTimeEnum.LUNCH,
                    bookingDate,
                    1L,
                    restaurant,
                    user
            );

            LocalDate realToday = LocalDate.of(2024, 1, 10);
            Date realNow = Date.from(realToday.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
            mockedDateUtil.when(DateTimeUtil::nowTruncate).thenReturn(realNow);

            ReservationUpdateException exception = assertThrows(ReservationUpdateException.class, () -> {
                new ReservationUpdateUseCase(reservation, reservationUpdateStatusPatchRequestDTO);
            });

            assertThat(exception.getMessage()).isEqualTo("Reservas anteriores ao dia de hoje não podem ser atualizadas.");
        }
    }

    @DisplayName("Teste de falha - Não deve conseguir atualizar uma Reserva Cancelada")
    @Test
    void shouldNotUpdateCanceledReservations() {
        ReservationUpdateStatusPatchRequestDTO reservationUpdateStatusPatchRequestDTO = ReservationTestFactory.loadValidUpdateRequestDTO();
        Restaurant restaurant = mock(Restaurant.class);
        User user = mock(User.class);
        when(restaurant.getLimitReservations(ReservationBookingTimeEnum.LUNCH)).thenReturn(100);
        LocalDate reservationDate = LocalDate.now();
        Date bookingDate = Date.from(reservationDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());

        Reservation reservation = new Reservation(
                ReservationBookingStatusEnum.CANCELED,
                ReservationBookingTimeEnum.LUNCH,
                bookingDate,
                1L,
                restaurant,
                user
        );

        ReservationUpdateException exception = assertThrows(ReservationUpdateException.class, () -> {
            new ReservationUpdateUseCase(reservation, reservationUpdateStatusPatchRequestDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("Reservas canceladas não podem ser atualizadas.");
    }
}
