package com.fiap.tech.challenge.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemBatchPutRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuBatchPutRequestDTO extends MenuRequestDTO {

    @Schema(description = "Itens do menu.")
    @NotEmpty(message = "Os itens do menu devem ser informados.")
    @JsonProperty("menuItems")
    private List<@Valid MenuItemBatchPutRequestDTO> menuItems;
}