package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemBatchPutRequestDTO extends MenuItemRequestDTO {

    @Schema(description = "Hash id do item do menu.", example = "265465616547661as61c65a4s15f4164")
    @Size(max = 255, message = "O número de caracteres máximo para o hash id do item do menu é 255 caracteres.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashId")
    private String hashId;
}