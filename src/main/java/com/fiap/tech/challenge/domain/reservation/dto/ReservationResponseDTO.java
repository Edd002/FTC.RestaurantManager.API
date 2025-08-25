package com.fiap.tech.challenge.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.reservation.enumerated.ReservationStatusEnum;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do pedido do restaurante.", example = "c85as4s8sd1as2c1c1a54s1c5v1a541s")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Status da reserva do restaurante.", example = "REQUESTED")
    @JsonProperty("status")
    private ReservationStatusEnum status;

    @Schema(description = "Restaurante do pedido.")
    @JsonProperty("restaurant")
    private RestaurantResponseDTO restaurant;

    @Schema(description = "Usuário do pedido.")
    @JsonProperty("user")
    private UserResponseDTO user;
}