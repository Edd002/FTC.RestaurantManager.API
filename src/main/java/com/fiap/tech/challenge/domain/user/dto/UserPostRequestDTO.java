package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.address.dto.AddressPostRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPostRequestDTO extends UserRequestDTO {

    @Schema(description = "Endereço do usuário.")
    @NotNull(message = "O endereço do usuário deve ser informado.")
    @JsonProperty("address")
    @Valid private AddressPostRequestDTO address;
}