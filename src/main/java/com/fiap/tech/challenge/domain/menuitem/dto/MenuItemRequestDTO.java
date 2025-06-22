package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictBooleanDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictPriceDeserializer;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public abstract class MenuItemRequestDTO extends BaseRequestDTO {

    @Schema(description = "Nome do item do menu.", example = "Espaguete", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o nome do item do menu é 255 caracteres.")
    @NotBlank(message = "O nome do item do menu não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("name")
    private String name;

    @Schema(description = "Descrição do item do menu.", example = "Espaguete à bolonhesa.", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a descrição do item do menu é 255 caracteres.")
    @NotBlank(message = "A descrição do item do menu não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("description")
    private String description;

    @Schema(description = "Preço do item do menu.", example = "19.99")
    @Positive(message = "O preço do item do menu deve ser positivo.")
    @NotNull(message = "O preço do item do menu não pode ser nulo.")
    @JsonDeserialize(using = StrictPriceDeserializer.class)
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Se o item do menu está disponível.", example = "true")
    @NotNull(message = "A disponibilidade do item do menu não pode ser nula.")
    @JsonDeserialize(using = StrictBooleanDeserializer.class)
    @JsonProperty("availability")
    private Boolean availability;

    @Schema(description = "URL da foto do item do menu.", example = "https://ftc-restaurant-manager-api.s3.amazonaws.com/1-admin/305-image_(9).png-20250614014808", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a foto do item do menu é 255 caracteres.")
    @NotBlank(message = "A foto do item do menu não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("photoUrl")
    private String photoUrl;
}