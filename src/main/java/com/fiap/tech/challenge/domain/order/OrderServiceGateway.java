package com.fiap.tech.challenge.domain.order;

import com.fiap.tech.challenge.domain.order.dto.OrderGetFilter;
import com.fiap.tech.challenge.domain.order.dto.OrderPostRequestDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderPutRequestDTO;
import com.fiap.tech.challenge.domain.order.dto.OrderResponseDTO;
import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.order.specification.OrderSpecificationBuilder;
import com.fiap.tech.challenge.domain.restaurant.RestaurantServiceGateway;
import com.fiap.tech.challenge.domain.restaurantuser.RestaurantUserServiceGateway;
import com.fiap.tech.challenge.global.base.BaseServiceGateway;
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
import java.util.Optional;

@Service
public class OrderServiceGateway extends BaseServiceGateway<IOrderRepository, Order> {

    private final IOrderRepository orderRepository;
    private final RestaurantServiceGateway restaurantServiceGateway;
    private final RestaurantUserServiceGateway restaurantUserServiceGateway;
    private final PageableBuilder pageableBuilder;
    private final ModelMapper modelMapperPresenter;

    @Autowired
    public OrderServiceGateway(IOrderRepository orderRepository, RestaurantServiceGateway restaurantServiceGateway, RestaurantUserServiceGateway restaurantUserServiceGateway, PageableBuilder pageableBuilder, ModelMapper modelMapperPresenter) {
        this.orderRepository = orderRepository;
        this.restaurantServiceGateway = restaurantServiceGateway;
        this.restaurantUserServiceGateway = restaurantUserServiceGateway;
        this.pageableBuilder = pageableBuilder;
        this.modelMapperPresenter = modelMapperPresenter;
    }

    @Transactional
    public OrderResponseDTO create(OrderPostRequestDTO orderPostRequestDTO) {
        return null;
    }

    @Transactional
    public OrderResponseDTO update(String hashId, OrderPutRequestDTO orderPutRequestDTO) {
        return null;
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
    }

    @Override
    public Order findByHashId(String hashId) {
        return super.findByHashId(hashId, String.format("O pedido com o hash id %s não foi encontrado.", hashId));
    }
}