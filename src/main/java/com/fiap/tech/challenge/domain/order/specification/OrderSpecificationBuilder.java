package com.fiap.tech.challenge.domain.order.specification;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.domain.order.dto.OrderGetFilter;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public class OrderSpecificationBuilder extends BasicSpecificationBuilder<Order, OrderSpecification, OrderGetFilter> {

    @Override
    protected void initParams(OrderGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getHashIdRestaurant())) {
            where("order.restaurant.hashId", SearchOperationEnum.EQUAL, filter.getHashIdRestaurant());
        }

        if (ValidationUtil.isNotNull(filter.getType())) {
            where("type", SearchOperationEnum.EQUAL, filter.getType());
        }

        if (ValidationUtil.isNotNull(filter.getStatus())) {
            where("status", SearchOperationEnum.EQUAL, filter.getStatus());
        }
    }

    @Override
    protected OrderSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new OrderSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected OrderSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new OrderSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}