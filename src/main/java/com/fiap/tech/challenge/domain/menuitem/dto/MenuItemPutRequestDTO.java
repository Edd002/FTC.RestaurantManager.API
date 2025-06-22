package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.menu.dto.MenuPutRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuItemPutRequestDTO extends MenuItemRequestDTO {

    @Schema(description = "Menu do item.")
    @NotNull(message = "O menu do item deve ser informado.")
    @JsonProperty("menu")
    @Valid private MenuPutRequestDTO menu;
}