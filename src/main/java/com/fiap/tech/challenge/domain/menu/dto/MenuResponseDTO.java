package com.fiap.tech.challenge.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MenuResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do menu.", example = "sas15f4a4564616454ss64164876as14")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;
}