package com.fiap.tech.challenge.domain.menuitem.dto;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class MenuItemGetFilter extends BasePaginationFilter {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    private String hashIdRestaurant;

    @Schema(description = "Nome do item do menu do restaurante.", example = "Espaguete")
    private String name;

    @Schema(description = "Descrição do item do menu do restaurante.", example = "Espaguete à bolonhesa.")
    private String description;

    @Schema(description = "Se o item do menu está disponível.", example = "true")
    private Boolean availability;

    public MenuItemGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}