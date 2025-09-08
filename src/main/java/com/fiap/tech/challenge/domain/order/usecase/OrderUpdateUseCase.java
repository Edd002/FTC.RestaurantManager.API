package com.fiap.tech.challenge.domain.order.usecase;

import com.fiap.tech.challenge.domain.order.dto.OrderUpdateStatusPatchRequestDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderUpdateTypePatchRequestDTO;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import lombok.NonNull;

public class OrderUpdateUseCase {

    private final Order order;

    public OrderUpdateUseCase(@NonNull Order existingOrder, @NonNull OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO) {
        this.order = rebuildOrder(existingOrder, orderUpdateStatusPatchRequestDTO);
    }

    public OrderUpdateUseCase(@NonNull Order existingOrder, @NonNull OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO) {
        this.order = rebuildOrder(existingOrder, orderUpdateTypePatchRequestDTO);
    }

    public OrderUpdateUseCase(@NonNull Order existingOrder, @NonNull OrderStatusEnum status) {
        this.order = rebuildOrder(existingOrder, status);
    }

    private Order rebuildOrder(Order existingOrder, OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO) {
        return existingOrder.rebuild(
                OrderStatusEnum.valueOf(orderUpdateStatusPatchRequestDTO.getStatus())
        );
    }

    private Order rebuildOrder(Order existingOrder, OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO) {
        return existingOrder.rebuild(
                OrderTypeEnum.valueOf(orderUpdateTypePatchRequestDTO.getType())
        );
    }

    private Order rebuildOrder(Order existingOrder, OrderStatusEnum status) {
        return existingOrder.rebuild(status);
    }

    public Order getRebuiltedOrder() {
        return this.order;
    }
}