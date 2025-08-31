package com.fiap.tech.challenge.domain.order.dto;

import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public final class OrderGetFilter extends BasePaginationFilter {

    @Schema(description = "Hash id do restaurante.", example = "c57469e0cf8245d0b9a9b3e39b303dc0")
    @NotBlank(message = "O hash id do restaurante não pode ser nulo ou em branco.")
    private String hashIdRestaurant;

    @Schema(description = "Status do pedido do restaurante.", example = "REQUESTED")
    private OrderStatusEnum status;

    @Schema(description = "Tipo do pedido do restaurante.", example = "DELIVERY")
    private OrderTypeEnum type;

    public OrderGetFilter(Integer pageNumber, Integer pageSize) {
        super(pageNumber, pageSize);
    }
}