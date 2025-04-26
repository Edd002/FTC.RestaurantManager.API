package com.fiap.tech.challenge.domain.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.city.dto.CityResponseDTO;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Setter
public class AddressResponseDTO extends BaseResponseDTO {

    @Schema(description = "Hash id do endereço.", example = "b062d4e007fe40368950d7d84927f3d4")
    @JsonProperty("hashId")
    private String hashId;

    @Schema(description = "Descrição do endereço.", example = "Rua dos Cadomblés")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Número do endereço.", example = "34")
    @JsonProperty("number")
    private String number;

    @Schema(description = "Complemento do endereço.", example = "Case A")
    @JsonProperty("complement")
    private String complement;

    @Schema(description = "Bairro do endereço.", example = "Santo Antônio")
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Schema(description = "CEP do endereço.", example = "35090-650")
    @JsonProperty("cep")
    private String cep;

    @Schema(description = "Caixa postal do endereço.", example = "1234-5678")
    @JsonProperty("postalCode")
    private String postalCode;

    @Schema(description = "cidade do endereço.")
    @JsonProperty("city")
    private CityResponseDTO city;
}