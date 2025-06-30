package com.fiap.tech.challenge.domain.user.dto;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class UserGetFilter extends BasePaginationFilter {

    @Schema(description = "Nome do usuário.", example = "Roberto Afonso")
    private String name;

    @Schema(description = "E-mail do usuário.", example = "robertoafonso@email.com")
    private String email;

    public UserGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}