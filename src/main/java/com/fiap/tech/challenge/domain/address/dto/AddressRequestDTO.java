package com.fiap.tech.challenge.domain.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public abstract class AddressRequestDTO extends BaseRequestDTO {

    @Schema(description = "Descrição do endereço.", example = "Rua dos Cadomblés")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A descrição do endereço não pode ser nula ou em branco.")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Número do endereço.", example = "34")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O número do endereço não pode ser nulo ou em branco.")
    @JsonProperty("number")
    private String number;

    @Schema(description = "Complemento do endereço.", example = "Case A")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @JsonProperty("complement")
    private String complement;

    @Schema(description = "Bairro do endereço.", example = "Santo Antônio")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O bairro do endereço não pode ser nulo ou em branco.")
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Schema(description = "CEP do endereço.", example = "35090-650")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "O CEP do endereço não pode ser nulo ou em branco.")
    @JsonProperty("cep")
    private String cep;

    @Schema(description = "Caixa postal do endereço.", example = "1234-5678")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A caixa postal do endereço não pode ser nula ou em branco.")
    @JsonProperty("postalCode")
    private String postalCode;

    @Schema(description = "Hash id da cidade do endereço.", example = "7edacee2252544519c29240daa51ee97")
    @JsonDeserialize(using = StrictStringDeserializer.class)
    @NotBlank(message = "A hash id da cidade do endereço não pode ser nulo ou em branco.")
    @JsonProperty("hashIdCity")
    private String hashIdCity;
}