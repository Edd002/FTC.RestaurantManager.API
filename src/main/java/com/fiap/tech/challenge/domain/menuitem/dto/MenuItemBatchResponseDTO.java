package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemBatchResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do item do menu.", example = "265465616547661as61c65a4s15f4164")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome do item do menu.", example = "Espaguete")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descrição do item do menu.", example = "Espaguete à bolonhesa.")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Preço do item do menu.", example = "19.99")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Se o item do menu está disponível.", example = "true")
    @JsonProperty("availability")
    private Boolean availability;

    @Schema(description = "URL da foto do item do menu.", example = "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808")
    @JsonProperty("photoUrl")
    private String photoUrl;
}