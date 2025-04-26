package com.fiap.tech.challenge.domain.state.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Setter
public class StateResponseDTO extends BaseResponseDTO {

    @Schema(description = "Nome do estado.", example = "Acre")
    @JsonProperty("name")
    private String name;

    @Schema(description = "UF do estado.", example = "AC")
    @JsonProperty("uf")
    private String uf;
}