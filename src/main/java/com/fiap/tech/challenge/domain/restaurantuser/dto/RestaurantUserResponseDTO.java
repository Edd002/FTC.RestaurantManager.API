package com.fiap.tech.challenge.domain.restaurantuser.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class RestaurantUserResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id da associação de usuário com restaurante.", example = "0ef6f7abed4b48e89c77901b1bcd4e33")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Restaurante associado ao usuário.")
    @JsonProperty("restaurant")
    private RestaurantResponseDTO restaurant;

    @Schema(description = "Usuário associado ao restaurante.")
    @JsonProperty("user")
    private UserResponseDTO user;
}