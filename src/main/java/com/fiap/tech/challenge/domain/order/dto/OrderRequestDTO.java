package com.fiap.tech.challenge.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.List;

@Getter
public abstract class OrderRequestDTO extends BaseRequestDTO {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o hash id do restaurante é 255 caracteres.")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;

    @Schema(description = "Hash ids de itens do menu.", type = "List", example = "[\"a6sac15as1a6d15as4fd21as12as4sde\", \"58qsa7df4ef4as4c15sa4d5a4sa8ef4a\"]")
    @NotEmpty(message = "Deve ser informado pelo menos um item do menu para o pedido.")
    @JsonProperty("hashIdsMenuItems")
    private List<@NotBlank(message = "Os itens do menu para o pedido não podem ser nulos ou em branco.") String> hashIdsMenuItems;

    @Schema(description = "Tipo do pedido.", example = "DELIVERY", maxLength = 255, allowableValues = { "DELIVERY", "PICKUP" })
    @Size(max = 255, message = "O número de caracteres máximo para o tipo do pedido é 255 caracteres.")
    @ValueOfEnum(enumClass = OrderTypeEnum.class, message = "Tipo do pedido inválido.")
    @NotBlank(message = "O tipo do pedido não pode ser nulo ou em branco.")
    @JsonProperty("type")
    private String type;
}