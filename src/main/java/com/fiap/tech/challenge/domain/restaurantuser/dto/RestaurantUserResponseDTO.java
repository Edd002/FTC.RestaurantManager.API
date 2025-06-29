package com.fiap.tech.challenge.domain.restaurantuser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantResponseDTO;
import com.fiap.tech.challenge.domain.user.dto.UserResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestaurantUserResponseDTO extends BaseResponseDTO {

    @Schema(description = "Restaurante associado ao usuário.")
    @JsonProperty("restaurant")
    private RestaurantResponseDTO restaurant;

    @Schema(description = "Usuário associado ao restaurante.")
    @JsonProperty("user")
    private UserResponseDTO user;
}