package com.fiap.tech.challenge.domain.restaurant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.address.dto.AddressPutRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantPutRequestDTO extends RestaurantRequestDTO {

    @Schema(description = "Endereço do restaurante.")
    @NotNull(message = "O endereço do restaurante deve ser informado.")
    @JsonProperty("address")
    @Valid private AddressPutRequestDTO address;
}