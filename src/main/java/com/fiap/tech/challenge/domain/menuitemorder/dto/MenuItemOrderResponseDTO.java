package com.fiap.tech.challenge.domain.menuitemorder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemOrderResponseDTO extends BaseResponseDTO {

    @Schema(description = "Item do menu.")
    @JsonProperty("menuItem")
    private MenuItemResponseDTO menuItem;
}