package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.address.dto.AddressPostRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserPostRequestDTO extends UserRequestDTO {

    @Schema(description = "Senha do usuário.", example = "robertoafonso2025")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A senha do usuário não pode ser nula ou em branco.")
    @JsonProperty("password")
    private String password;

    @Schema(description = "Endereço do usuário.")
    @NotNull(message = "O endereço do usuário deve ser informado.")
    @JsonProperty("address")
    @Valid private AddressPostRequestDTO address;
}