package com.fiap.tech.challenge.domain.city.dto;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class CityGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome da cidade.", example = "Ariquemes")
    private String name;

    @Schema(description = "UF do estado da cidade.", example = "AC")
    private String ufState;

    public CityGetFilter(int pageNumber, int pageSize) {
        super(pageNumber, pageSize);
    }
}