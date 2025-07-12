package com.fiap.tech.challenge.domain.usertype.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeResponseDTO extends BaseResponseDTO {

    @Schema(description = "Nome do tipo de usuário.", example = "EMPLOYEE")
    @JsonProperty("name")
    private String name;
}