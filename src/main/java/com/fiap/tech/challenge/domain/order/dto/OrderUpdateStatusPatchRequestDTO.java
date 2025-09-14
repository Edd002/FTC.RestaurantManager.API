package com.fiap.tech.challenge.domain.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.global.base.dto.BaseRequestDTO;
import com.fiap.tech.challenge.global.util.deserializer.StrictStringNormalizeSpaceDeserializer;
import com.fiap.tech.challenge.global.util.enumerated.validation.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderUpdateStatusPatchRequestDTO extends BaseRequestDTO {

    @Schema(description = "Hash id do usuário.", example = "98e91489d4234e7fba35cc9842828d16", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o hash id do usuário é 255 caracteres.")
    @NotBlank(message = "A hash id do usuário não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashIdUser")
    private String hashIdUser;

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0", maxLength = 255)
    @Size(max = 255, message = "O número de caracteres máximo para o hash id do restaurante é 255 caracteres.")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    @JsonDeserialize(using = StrictStringNormalizeSpaceDeserializer.class)
    @JsonProperty("hashIdRestaurant")
    private String hashIdRestaurant;

    @Schema(description = "Status do pedido.", example = "CONFIRMED", maxLength = 255, allowableValues = { "REQUESTED", "CONFIRMED", "WAITING_FOR_PICKUP", "ON_DELIVERY_ROUTE", "DELIVERED", "CANCELED" })
    @Size(max = 255, message = "O número de caracteres máximo para o status do pedido é 255 caracteres.")
    @ValueOfEnum(enumClass = OrderStatusEnum.class, message = "Status do pedido inválido.")
    @NotBlank(message = "O status do pedido não pode ser nulo ou em branco.")
    @JsonProperty("status")
    private String status;
}