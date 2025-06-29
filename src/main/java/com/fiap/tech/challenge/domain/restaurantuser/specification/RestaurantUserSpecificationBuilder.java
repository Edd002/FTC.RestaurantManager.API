package com.fiap.tech.challenge.domain.restaurantuser.specification;

import com.fiap.tech.challenge.domain.restaurantuser.dto.RestaurantUserGetFilter;
import com.fiap.tech.challenge.domain.restaurantuser.entity.RestaurantUser;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
public class RestaurantUserSpecificationBuilder extends BasicSpecificationBuilder<RestaurantUser, RestaurantUserSpecification, RestaurantUserGetFilter> {

    @Override
    protected void initParams(RestaurantUserGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getRestaurantName())) {
            where("restaurant.name", SearchOperationEnum.LIKE, filter.getRestaurantName());
        }

        if (ValidationUtil.isNotBlank(filter.getUserName())) {
            where("user.name", SearchOperationEnum.LIKE, filter.getUserName());
        }
    }

    @Override
    protected RestaurantUserSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new RestaurantUserSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected RestaurantUserSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new RestaurantUserSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}