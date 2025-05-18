package com.fiap.tech.challenge.domain.jwt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponseDTO extends BaseResponseDTO {

    @Schema(description = "Login usuário do JWT.", example = "roberto_afonso_001")
    @JsonProperty("userLogin")
    private String userLogin;

    @Schema(description = "Bearer token.", example = "1a1a1a1a1a.2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a2a.3a3a3a3a3a3a3a3a")
    @JsonProperty("bearerToken")
    private String bearerToken;
}