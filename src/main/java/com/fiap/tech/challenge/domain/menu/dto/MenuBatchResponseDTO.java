package com.fiap.tech.challenge.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemBatchResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MenuBatchResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;

    @Schema(description = "Itens do menu.")
    @JsonProperty("menuItems")
    private List<MenuItemBatchResponseDTO> menuItems;
}