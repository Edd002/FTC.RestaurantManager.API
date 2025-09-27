package com.fiap.tech.challenge.domain.unit.reservation;

import com.fiap.tech.challenge.domain.factory.ReservationTestFactory;
import com.fiap.tech.challenge.domain.factory.RestaurantTestFactory;
import com.fiap.tech.challenge.domain.factory.UserTestFactory;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.entity.Reservation;
import com.fiap.tech.challenge.domain.reservation.usecase.ReservationCreateUseCase;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.exception.ReservationCreateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum.REQUESTED;
import static com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum.BREAKFAST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationCreateUseCaseTest {

    @DisplayName("Teste de sucesso - Deve conseguir criar uma Reserva no Restaurante com sucesso")
    @Test
    void shouldCreateReservationSuccessfully(){
        ReservationPostRequestDTO reservationPostRequestDTO = ReservationTestFactory.loadValidPostRequestDTO();
        User client = UserTestFactory.loadEntityClient();
        Restaurant restaurant = RestaurantTestFactory.loadEntityRestaurant();
        ReservationCreateUseCase reservationCreateUseCase = new ReservationCreateUseCase(client, restaurant, reservationPostRequestDTO);

        Reservation reservation = reservationCreateUseCase.getBuiltedReservation();

        assertThat(reservation).isNotNull();
        assertThat(reservation.getBookingStatus()).isEqualTo(REQUESTED);
        assertThat(reservation.getBookingTime()).isEqualTo(BREAKFAST);
        assertThat(reservation.getBookingQuantity()).isEqualTo(2);
        LocalDate reservationDate = LocalDate.now();
        assertThat(reservation.getBookingDate()).hasYear(reservationDate.getYear()).hasMonth(reservationDate.getMonthValue()).hasDayOfMonth(reservationDate.getDayOfMonth());
        assertThat(reservation.getRestaurant()).isEqualTo(restaurant);
        assertThat(reservation.getUser()).isEqualTo(client);
    }

    @DisplayName("Teste de falha - Não deve conseguir criar uma Reserva no Restaurante com data anterior a hoje")
    @Test
    void shouldNotCreateReservationSuccessfullyWithInvalidDate() {
        ReservationPostRequestDTO reservationPostRequestDTO = ReservationTestFactory.loadInvalidByDatePostRequestDTO();
        User client = UserTestFactory.loadEntityClient();
        Restaurant restaurant = RestaurantTestFactory.loadEntityRestaurant();

        ReservationCreateException exception = assertThrows(ReservationCreateException.class, () -> {
            new ReservationCreateUseCase(client, restaurant, reservationPostRequestDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("A reserva não pode ser realizada para um dia anterior à hoje.");
    }

    @DisplayName("Teste de falha - Não deve conseguir criar uma Reserva no Restaurante quando a quantidade de reservas exceder o limite")
    @Test
    void shouldNotCreateReservationSuccessfullyWithExceededLimit() {
        ReservationPostRequestDTO reservationPostRequestDTO = ReservationTestFactory.loadInvalidByBookingQuantityPostRequestDTO();
        User client = UserTestFactory.loadEntityClient();
        Restaurant restaurant = RestaurantTestFactory.loadEntityRestaurant();

        ReservationCreateException exception = assertThrows(ReservationCreateException.class, () -> {
            new ReservationCreateUseCase(client, restaurant, reservationPostRequestDTO);
        });

        assertThat(exception.getMessage()).isEqualTo("A quantidade de reservas solicitadas ultrapassa o limite que o restaurante tem disponível.");
    }
}
