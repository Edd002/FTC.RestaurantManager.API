package com.fiap.tech.challenge.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.address.dto.AddressResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Setter
public class UserResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do usuário.", example = "103c72619967461987ec61537515073d")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome do usuário.", example = "Roberto Afonso")
    @JsonProperty("name")
    private String name;

    @Schema(description = "E-mail do usuário.", example = "robertoafonso@email.com")
    @JsonProperty("email")
    private String email;

    @Schema(description = "Login do usuário.", example = "roberto_afonso_001")
    @JsonProperty("login")
    private String login;

    @Schema(description = "Endereço do usuário.")
    @JsonProperty("address")
    private AddressResponseDTO address;
}