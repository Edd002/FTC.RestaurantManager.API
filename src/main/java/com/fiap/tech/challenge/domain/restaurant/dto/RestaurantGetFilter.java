package com.fiap.tech.challenge.domain.restaurant.dto;

import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class RestaurantGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome do restaurante.", example = "Pizzaria Alonzo")
    private String name;

    @Schema(description = "Tipo do restaurante.", example = "BAKERY")
    private RestaurantTypeEnum type;

    public RestaurantGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}