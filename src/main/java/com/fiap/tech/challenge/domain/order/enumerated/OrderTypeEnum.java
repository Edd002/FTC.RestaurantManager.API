package com.fiap.tech.challenge.domain.order.enumerated;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import lombok.Getter;

@Getter
public enum OrderTypeEnum {

    DELIVERY("Order type for delivery"),
    PICKUP("Order type for pickup");

    private final String description;

    OrderTypeEnum(String description) {
        this.description = description;
    }

    public static boolean isForDelivery(OrderTypeEnum type) {
        return ValidationUtil.isNotNull(type) && type.equals(DELIVERY);
    }

    public static boolean isForPickup(OrderTypeEnum type) {
        return ValidationUtil.isNotNull(type) && type.equals(PICKUP);
    }
}