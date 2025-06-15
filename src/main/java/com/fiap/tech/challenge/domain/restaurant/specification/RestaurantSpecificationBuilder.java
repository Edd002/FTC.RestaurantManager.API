package com.fiap.tech.challenge.domain.restaurant.specification;

import com.fiap.tech.challenge.domain.restaurant.dto.RestaurantGetFilter;
import com.fiap.tech.challenge.domain.restaurant.entity.Restaurant;
import com.fiap.tech.challenge.domain.restaurant.enumerated.RestaurantTypeEnum;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public class RestaurantSpecificationBuilder extends BasicSpecificationBuilder<Restaurant, RestaurantSpecification, RestaurantGetFilter> {

    @Override
    protected void initParams(RestaurantGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getName())) {
            where("name", SearchOperationEnum.LIKE, filter.getName());
        }

        if (ValidationUtil.isNotBlank(filter.getType())) {
            where("type", SearchOperationEnum.EQUAL, RestaurantTypeEnum.valueOf(filter.getType()));
        }
    }

    @Override
    protected RestaurantSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new RestaurantSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected RestaurantSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new RestaurantSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}