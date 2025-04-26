package com.fiap.tech.challenge.domain.city.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.state.dto.StateResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Setter
public class CityResponseDTO extends BaseResponseDTO {

    @Schema(description = "Nome da cidade.", example = "Ariquemes")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Estado da cidade.")
    @JsonProperty("state")
    private StateResponseDTO state;
}