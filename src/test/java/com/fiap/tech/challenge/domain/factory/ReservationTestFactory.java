package com.fiap.tech.challenge.domain.factory;

import com.fiap.tech.challenge.domain.reservation.dto.ReservationPostRequestDTO;
import com.fiap.tech.challenge.domain.reservation.dto.ReservationUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.global.util.JsonUtil;

import java.time.LocalDate;

public class ReservationTestFactory {

    private static final String PATH_RESOURCE_RESERVATION = "/mock/reservation/reservation.json";

    public static ReservationPostRequestDTO loadValidPostRequestDTO() {
        return JsonUtil.objectFromJsonWithReplacement(
                PATH_RESOURCE_RESERVATION,
                "UPDATE_DATE",
                LocalDate.now().toString(),
                "reservationPostRequestDTO",
                ReservationPostRequestDTO.class
        );
    }

    public static ReservationPostRequestDTO loadInvalidByDatePostRequestDTO() {
        return JsonUtil.objectFromJsonWithReplacement(
                PATH_RESOURCE_RESERVATION,
                "UPDATE_DATE",
                LocalDate.now().minusDays(10).toString(),
                "reservationPostRequestDTO",
                ReservationPostRequestDTO.class
        );
    }

    public static ReservationPostRequestDTO loadInvalidByBookingQuantityPostRequestDTO() {
        return JsonUtil.objectFromJsonWithReplacement(
                PATH_RESOURCE_RESERVATION,
                "UPDATE_DATE",
                LocalDate.now().toString(),
                "reservationPostRequestDTOInvalid",
                ReservationPostRequestDTO.class
        );
    }

    public static ReservationUpdateStatusPatchRequestDTO loadValidUpdateRequestDTO() {
        return JsonUtil.objectFromJson(
                "reservationUpdateStatusPatchRequestDTO",
                PATH_RESOURCE_RESERVATION,
                ReservationUpdateStatusPatchRequestDTO.class
        );
    }
}
