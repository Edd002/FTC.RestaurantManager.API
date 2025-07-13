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

    @Schema(description = "Hash id do tipo de usuário.", example = "c4as4fa748eas48f1f1h54as1a541fa4")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Nome do tipo de usuário.", example = "EMPLOYEE")
    @JsonProperty("name")
    private String name;
}