package com.fiap.tech.challenge.domain.restaurantuser.dto;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantUserGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome do restaurante.", example = "Pizzaria Alonzo")
    private String restaurantName;

    @Schema(description = "Nome do usuário.", example = "Roberto Afonso")
    private String userName;

    public RestaurantUserGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}