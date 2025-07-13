package com.fiap.tech.challenge.domain.usertype.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceUpperCaseDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public abstract class UserTypeRequestDTO extends BaseRequestDTO {

    @Schema(description = "Nome do tipo de usuário.", example = "EMPLOYEE", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o nome do tipo de usuário é 255 caracteres.")
    @NotBlank(message = "O nome do tipo de usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceUpperCaseDeserializer.class)
    @JsonProperty("name")
    private String name;
}