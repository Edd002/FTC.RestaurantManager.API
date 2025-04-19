package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public abstract class UserRequestDTO extends BaseRequestDTO {

    @Schema(description = "Hash id do usuário.", example = "103c72619967461987ec61537515073d")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O hash id do usuário não pode ser nulo ou em branco.")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome do usuário.", example = "Roberto Afonso")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O nome do usuário não pode ser nulo ou em branco.")
    @JsonProperty("name")
    private String name;

    @Schema(description = "E-mail do usuário.", example = "robertoafonso@email.com")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O e-mail do usuário não pode ser nulo ou em branco.")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Login do usuário.", example = "roberto_afonso_001")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O login do usuário não pode ser nulo ou em branco.")
    @JsonProperty("login")
    private String login;

    @Schema(description = "Senha do usuário.", example = "robertoafonso2025")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A senha do usuário não pode ser nulo ou em branco.")
    @JsonProperty("password")
    private String password;
}