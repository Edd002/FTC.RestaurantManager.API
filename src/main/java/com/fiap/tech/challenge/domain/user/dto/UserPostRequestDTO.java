package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.address.dto.AddressPostRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserPostRequestDTO extends UserRequestDTO {

    @Schema(description = "Senha do usuário.", example = "robertoafonso2025", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a senha do usuário é 255 caracteres.")
    @NotBlank(message = "A senha do usuário não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("password")
    private String password;

    @Schema(description = "Endereço do usuário.")
    @NotNull(message = "O endereço do usuário deve ser informado.")
    @JsonProperty("address")
    @Valid private AddressPostRequestDTO address;
}