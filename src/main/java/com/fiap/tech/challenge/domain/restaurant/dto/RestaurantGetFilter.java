package com.fiap.tech.challenge.domain.restaurant.dto;

import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class RestaurantGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome do restaurante.", example = "Pizzaria Alonzo")
    private String name;

    @Schema(description = "Tipo do restaurante.", example = "BAKERY")
    @ValueOfEnum(enumClass = RestaurantTypeEnum.class, message = "Tipo do restaurante inválido.")
    private String type;

    public RestaurantGetFilter(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
    }
}