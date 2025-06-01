package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserUpdatePasswordPatchRequestDTO extends BaseRequestDTO {

    @Schema(description = "Senha atual do usuário.", example = "robertoafonso2025", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a senha atual do usuário é 255 caracteres.")
    @NotBlank(message = "A senha atual do usuário não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("actualPassword")
    private String actualPassword;

    @Schema(description = "Nova senha do usuário.", example = "robertoafonso2026", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a nova senha do usuário é 255 caracteres.")
    @NotBlank(message = "A nova senha do usuário não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("newPassword")
    private String newPassword;

    @Schema(description = "Confirmação da nova senha do usuário.", example = "robertoafonso2026", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a confirmação de nova senha do usuário é 255 caracteres.")
    @NotBlank(message = "A confirmação da nova senha do usuário não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("newPasswordConfirmation")
    private String newPasswordConfirmation;
}
