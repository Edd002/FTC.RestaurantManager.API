package com.fiap.tech.challenge.domain.order.usecase;

import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.order.dto.OrderPostRequestDTO;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.order.enumerated.OrderStatusEnum;
import com.fiap.tech.challenge.domain.order.enumerated.OrderTypeEnum;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.user.entity.User;
import lombok.NonNull;

import java.util.List;

public class OrderCreateUseCase {

    private final Order order;

    public OrderCreateUseCase(@NonNull User loggedUser, @NonNull Restaurant restaurant, @NonNull List<MenuItem> menuItems, @NonNull OrderPostRequestDTO orderPostRequestDTO) {
        this.order = buildOrder(loggedUser, restaurant, menuItems, orderPostRequestDTO);
    }

    private Order buildOrder(User loggedUser, Restaurant restaurant, List<MenuItem> menuItems, OrderPostRequestDTO orderPostRequestDTO) {
        return new Order(
                OrderStatusEnum.REQUESTED,
                OrderTypeEnum.valueOf(orderPostRequestDTO.getType()),
                menuItems,
                restaurant,
                loggedUser
        );
    }

    public Order getBuiltedOrder() {
        return this.order;
    }
}