package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.EmailValidatorUtil;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceUpperCaseDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public abstract class UserRequestDTO extends BaseRequestDTO {

    @Schema(description = "Nome do usuário.", example = "Roberto Afonso", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o nome do usuário é 255 caracteres.")
    @NotBlank(message = "O nome do usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("name")
    private String name;

    @Schema(description = "E-mail do usuário.", example = "robertoafonso@email.com", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o email do usuário é 255 caracteres.")
    @Email(regexp = EmailValidatorUtil.EMAIL_REGEXP, message = "E-mail inválido.")
    @NotBlank(message = "O e-mail do usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("email")
    private String email;

    @Schema(description = "Login do usuário.", example = "roberto_afonso_001", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o login do usuário é 255 caracteres.")
    @NotBlank(message = "O login do usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("login")
    private String login;

    @Schema(description = "Tipo de usuário.", example = "OWNER", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o tipo de usuário é 255 caracteres.")
    @NotBlank(message = "O tipo de usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceUpperCaseDeserializer.class)
    @JsonProperty("type")
    private String type;
}