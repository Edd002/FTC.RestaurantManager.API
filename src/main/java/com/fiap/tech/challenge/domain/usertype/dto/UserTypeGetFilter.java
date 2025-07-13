package com.fiap.tech.challenge.domain.usertype.dto;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserTypeGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome do tipo de usuário.", example = "EMPLOYEE")
    private String name;

    public UserTypeGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}