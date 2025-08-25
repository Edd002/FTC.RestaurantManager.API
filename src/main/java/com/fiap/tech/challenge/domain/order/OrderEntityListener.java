package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.entity.Order;
import jakarta.persistence.PostLoad;
import org.apache.commons.lang3.SerializationUtils;

public class OrderEntityListener {

    @PostLoad
    public void postLoad(Order orderEntity) {
        orderEntity.saveState(SerializationUtils.clone(orderEntity));
    }
}