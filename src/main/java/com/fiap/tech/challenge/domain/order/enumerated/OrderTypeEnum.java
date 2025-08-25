package com.fiap.tech.challenge.domain.order.enumerated;

import lombok.Getter;

@Getter
public enum OrderTypeEnum {

    DELIVERY("Order type for delivery"),
    PICKUP("Order type for pickup");

    private final String description;

    OrderTypeEnum(String description) {
        this.description = description;
    }
}