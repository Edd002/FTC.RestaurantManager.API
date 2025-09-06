package com.fiap.tech.challenge.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingStatusEnum;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationBookingTimeEnum;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do pedido do restaurante.", example = "c85as4s8sd1as2c1c1a54s1c5v1a541s")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Status da reserva do restaurante.", example = "REQUESTED")
    @JsonProperty("bookingStatus")
    private ReservationBookingStatusEnum bookingStatus;

    @Schema(description = "Horário da reserva do restaurante.", example = "BREAKFAST")
    @JsonProperty("bookingTime")
    private ReservationBookingTimeEnum bookingTime;

    @Schema(description = "Data da reserva do restaurante.", example = "30/08/2025")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "GMT-3")
    @JsonProperty("bookingDate")
    private Date bookingDate;

    @Schema(description = "Quantidade de reservas do restaurante.", example = "3")
    @JsonProperty("bookingQuantity")
    private Long bookingQuantity;

    @Schema(description = "Restaurante do pedido.")
    @JsonProperty("restaurant")
    private RestaurantResponseDTO restaurant;

    @Schema(description = "Usuário do pedido.")
    @JsonProperty("user")
    private UserResponseDTO user;
}