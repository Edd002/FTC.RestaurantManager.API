package com.fiap.tech.challenge.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateTypePatchRequestDTO extends BaseRequestDTO {

    @Schema(description = "Tipo do pedido.", example = "DELIVERY", maxLength = 255, allowableValues = { "DELIVERY", "PICKUP" })
    @Size(max = 255, message = "O número de caracteres máximo para o tipo do pedido é 255 caracteres.")
    @ValueOfEnum(enumClass = OrderTypeEnum.class, message = "Tipo do pedido inválido.")
    @NotBlank(message = "O tipo do pedido não pode ser nulo ou em branco.")
    @JsonProperty("type")
    private String type;
}