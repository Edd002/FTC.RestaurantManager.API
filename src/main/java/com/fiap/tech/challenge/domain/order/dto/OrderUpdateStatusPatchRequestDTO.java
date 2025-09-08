package com.fiap.tech.challenge.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateStatusPatchRequestDTO extends BaseRequestDTO {

    @Schema(description = "Status do pedido.", example = "CONFIRMED", maxLength = 255, allowableValues = { "REQUESTED", "CONFIRMED", "WAITING_FOR_PICKUP", "ON_DELIVERY_ROUTE", "DELIVERED", "CANCELED" })
    @Size(max = 255, message = "O número de caracteres máximo para o status do pedido é 255 caracteres.")
    @ValueOfEnum(enumClass = OrderStatusEnum.class, message = "Status do pedido inválido.")
    @NotBlank(message = "O status do pedido não pode ser nulo ou em branco.")
    @JsonProperty("status")
    private String status;
}