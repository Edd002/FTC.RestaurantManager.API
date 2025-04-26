package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UserUpdatePasswordPatchRequestDTO extends BaseRequestDTO {

    @Schema(description = "Senha atual do usuário.", example = "robertoafonso2025")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A senha atual do usuário não pode ser nula ou em branco.")
    @JsonProperty("actualPassword")
    private String actualPassword;

    @Schema(description = "Nova senha do usuário.", example = "robertoafonso2026")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A nova senha do usuário não pode ser nula ou em branco.")
    @JsonProperty("newPassword")
    private String newPassword;

    @Schema(description = "Confirmação da nova senha do usuário.", example = "robertoafonso2026")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A confirmação da nova senha do usuário não pode ser nula ou em branco.")
    @JsonProperty("newPasswordConfirmation")
    private String newPasswordConfirmation;
}
