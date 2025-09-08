package com.fiap.tech.challenge.domain.order.enumerated;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    REQUESTED(1, "Order status requested"),
    CONFIRMED(2, "Order status confirmed"),
    WAITING_FOR_PICKUP(3, "Order status waiting for pickup"),
    ON_DELIVERY_ROUTE(4, "Order status on delivery route"),
    DELIVERED(5, "Order status delivered"),
    CANCELED(6, "Order status canceled");

    private final Integer sequence;
    private final String description;

    OrderStatusEnum(Integer sequence, String description) {
        this.sequence = sequence;
        this.description = description;
    }

    public static boolean isForPickup(OrderStatusEnum status) {
        return ValidationUtil.isNotNull(status) && (status.equals(WAITING_FOR_PICKUP));
    }

    public static boolean isForDelivery(OrderStatusEnum status) {
        return ValidationUtil.isNotNull(status) && (status.equals(ON_DELIVERY_ROUTE));
    }

    public static boolean isDelivered(OrderStatusEnum status) {
        return ValidationUtil.isNotNull(status) && (status.equals(DELIVERED));
    }

    public static boolean isCanceled(OrderStatusEnum status) {
        return ValidationUtil.isNotNull(status) && (status.equals(CANCELED));
    }

    public static boolean isBefore(OrderStatusEnum status1, OrderStatusEnum status2) {
        return (ValidationUtil.isNotNull(status1) && ValidationUtil.isNotNull(status2)) && (status1.getSequence() < status2.getSequence());
    }
}