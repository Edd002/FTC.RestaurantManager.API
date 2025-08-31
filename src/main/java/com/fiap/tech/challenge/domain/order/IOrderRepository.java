package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.global.base.IBaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends IBaseRepository<Order> {
}