package com.fiap.tech.challenge.domain.order.enumerated;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    REQUESTED("Order status requested"),
    CONFIRMED("Order status confirmed"),
    WAITING_FOR_PICKUP("Order status waiting for pickup"),
    ON_DELIVERY_ROUTE("Order status on delivery route"),
    DELIVERED("Order status delivered");

    private final String description;

    OrderStatusEnum(String description) {
        this.description = description;
    }
}