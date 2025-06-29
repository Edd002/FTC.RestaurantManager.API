package com.fiap.tech.challenge.domain.city.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.state.dto.StateResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CityResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id da cidade.", example = "d56f6fa310484355a881983b3942ec6b")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome da cidade.", example = "Ariquemes")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Estado da cidade.")
    @JsonProperty("state")
    private StateResponseDTO state;
}