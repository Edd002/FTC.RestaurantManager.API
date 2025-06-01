package com.fiap.tech.challenge.domain.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtGeneratePostRequestDTO {

    @Schema(description = "Login do usuário.", example = "roberto_afonso_001", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o login do usuário é 255 caracteres.")
    @NotBlank(message = "O login do usuário deve ser informado.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("login")
    private String login;

    @Schema(description = "Senha do usuário.", example = "robertoafonso2025", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a senha do usuário é 255 caracteres.")
    @NotBlank(message = "A senha do usuário deve ser informada.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("password")
    private String password;
}