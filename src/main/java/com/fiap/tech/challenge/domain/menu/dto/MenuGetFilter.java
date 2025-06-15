package com.fiap.tech.challenge.domain.menu.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MenuGetFilter extends BasePaginationFilter {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;

    @Schema(description = "Nome do item do menu do restaurante.", example = "Espaguete")
    @JsonProperty("name")
    private String nameMenuItemRestaurant;

    public MenuGetFilter(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
    }
}