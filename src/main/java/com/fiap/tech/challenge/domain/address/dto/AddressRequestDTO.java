package com.fiap.tech.challenge.domain.address.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public abstract class AddressRequestDTO extends BaseRequestDTO {

    @Schema(description = "Descrição do endereço.", example = "Rua dos Cadomblés", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para a descrição do endereço é 255 caracteres.")
    @NotBlank(message = "A descrição do endereço não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("description")
    private String description;

    @Schema(description = "Número do endereço.", example = "34", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o número do endereço é 255 caracteres.")
    @NotBlank(message = "O número do endereço não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("number")
    private String number;

    @Schema(description = "Complemento do endereço.", example = "Casa A", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o complemento do endereço é 255 caracteres.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("complement")
    private String complement;

    @Schema(description = "Bairro do endereço.", example = "Santo Antônio", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o bairro do endereço é 255 caracteres.")
    @NotBlank(message = "O bairro do endereço não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("neighborhood")
    private String neighborhood;

    @Schema(description = "CEP do endereço.", example = "35090-650", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o CEP do endereço é 255 caracteres.")
    @NotBlank(message = "O CEP do endereço não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("cep")
    private String cep;

    @Schema(description = "Caixa postal do endereço.", example = "1234-5678", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o código postal do endereço é 255 caracteres.")
    @NotBlank(message = "A caixa postal do endereço não pode ser nula ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("postalCode")
    private String postalCode;

    @Schema(description = "Hash id da cidade do endereço.", example = "7edacee2252544519c29240daa51ee97", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o hash id da cidade do endereço é 255 caracteres.")
    @NotBlank(message = "A hash id da cidade do endereço não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashIdCity")
    private String hashIdCity;
}