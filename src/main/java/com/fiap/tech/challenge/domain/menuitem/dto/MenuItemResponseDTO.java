package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MenuItemResponseDTO {

    @Schema(description = "Nome do item do menu.", example = "Espaguete", maxLength = 255)
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descrição do item do menu.", example = "Espaguete à bolonhesa.", maxLength = 255)
    @JsonProperty("description")
    private String description;

    @Schema(description = "Preço do item do menu.", example = "25.00")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Se o item do menu está disponível.", example = "true")
    @JsonProperty("availability")
    private Boolean availability;

    @Schema(description = "URL da foto do item do menu.", example = "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808", maxLength = 255)
    @JsonProperty("photoUrl")
    private String photoUrl;
}