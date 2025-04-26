package com.fiap.tech.challenge.domain.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Setter
public class JwtResponseDTO extends BaseResponseDTO {

    @Schema(description = "Nome do token de usuário.", example = "Token Gerado no User Manager")
    @JsonProperty("tokenAplicacaoNome")
    private String userTokenName;

    @Schema(description = "Bearer token.", example = "1a1a1a1a1a.2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a.3a3a3a3a3a3a3a3a")
    @JsonProperty("bearerToken")
    private String bearerToken;
}