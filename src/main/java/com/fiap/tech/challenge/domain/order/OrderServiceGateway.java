package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.menuitem.MenuItemServiceGateway;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.domain.order.dto.*;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.order.specification.OrderSpecificationBuilder;
import com.fiap.tech.challenge.domain.order.usecase.OrderCreateUseCase;
import com.fiap.tech.challenge.domain.order.usecase.OrderUpdateUseCase;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.domain.user.authuser.AuthUserContextHolder;
import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
import com.fiap.tech.challenge.global.exception.EntityNotFoundException;
import com.fiap.tech.challenge.global.search.builder.PageableBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceGateway extends BaseServiceGateway<IOrderRepository, Order> {

    private final IOrderRepository orderRepository;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final MenuItemServiceGateway menuItemServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public OrderServiceGateway(IOrderRepository orderRepository, RestaurantUserServiceGateway restaurantUserServiceGateway, MenuItemServiceGateway menuItemServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.orderRepository = orderRepository;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.menuItemServiceGateway = menuItemServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public OrderResponseDTO create(OrderPostRequestDTO orderPostRequestDTO) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Restaurant existingRestaurant = restaurantUserServiceGateway.findByRestaurantHashIdAndUser(orderPostRequestDTO.getHashIdRestaurant(), loggedUser).getRestaurant();
        List<MenuItem> menuItems = menuItemServiceGateway.findAllByHashIdIn(orderPostRequestDTO.getHashIdsMenuItems());
        Order newOrder = new OrderCreateUseCase(loggedUser, existingRestaurant, menuItems, orderPostRequestDTO).getBuiltedOrder();
        return modelMapperPresenter.map(save(newOrder), OrderResponseDTO.class);
    }

    @Transactional
    public OrderResponseDTO update(String hashId, OrderUpdateStatusPatchRequestDTO orderUpdateStatusPatchRequestDTO) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Order existingOrder = findByHashIdAndUser(hashId, loggedUser);
        Order updatedOrder = new OrderUpdateUseCase(existingOrder, orderUpdateStatusPatchRequestDTO).getRebuiltedOrder();
        return modelMapperPresenter.map(save(updatedOrder), OrderResponseDTO.class);
    }

    @Transactional
    public OrderResponseDTO update(String hashId, OrderUpdateTypePatchRequestDTO orderUpdateTypePatchRequestDTO) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        Order existingOrder = findByHashIdAndUser(hashId, loggedUser);
        Order updatedOrder = new OrderUpdateUseCase(existingOrder, orderUpdateTypePatchRequestDTO).getRebuiltedOrder();
        return modelMapperPresenter.map(save(updatedOrder), OrderResponseDTO.class);
    }

    @Transactional
    public Page<OrderResponseDTO> find(OrderGetFilter filter) {
        Pageable pageable = pageableBuilder.build(filter);
        Optional<Specification<Order>> specification = new OrderSpecificationBuilder().build(filter);
        return specification
                .map(spec -> findAll(spec, pageable))
                .orElseGet(() -> new PageImpl<>(new ArrayList<>()))
                .map(order -> modelMapperPresenter.map(order, OrderResponseDTO.class));
    }

    @Transactional
    public OrderResponseDTO find(String hashId) {
        return modelMapperPresenter.map(findByHashId(hashId), OrderResponseDTO.class);
    }

    @Transactional
    public void delete(String hashId) {
        User loggedUser = AuthUserContextHolder.getAuthUser();
        findByHashIdAndUser(hashId, loggedUser);
        flush();
        deleteByHashId(hashId);
    }

    @Transactional
    public Order findByHashIdAndUser(String hashId, User user) {
        return orderRepository.findByHashIdAndUser(hashId, user).orElseThrow(() -> new EntityNotFoundException(String.format("Nenhum pedido para o usuário com hash id %s foi encontrado.", hashId)));
    }

    @Override
    public Order findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O pedido com o hash id %s não foi encontrado.", hashId));
    }
}