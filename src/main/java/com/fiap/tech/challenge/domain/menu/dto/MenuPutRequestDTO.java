package com.fiap.tech.challenge.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemPutRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class MenuPutRequestDTO extends MenuRequestDTO {

    @Schema(description = "Itens do menu.")
    @JsonProperty("menuItems")
    private List<@Valid MenuItemPutRequestDTO> menuItems;
}