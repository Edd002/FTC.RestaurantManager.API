package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceGateway extends BaseServiceGateway<IOrderRepository, Order> {
}