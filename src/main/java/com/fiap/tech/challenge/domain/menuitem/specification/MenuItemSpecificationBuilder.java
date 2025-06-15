package com.fiap.tech.challenge.domain.menuitem.specification;

import com.fiap.tech.challenge.domain.menuitem.dto.MenuItemGetFilter;
import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;

public class MenuItemSpecificationBuilder extends BasicSpecificationBuilder<MenuItem, MenuItemSpecification, MenuItemGetFilter> {

    @Override
    protected void initParams(MenuItemGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getHashIdRestaurant())) {
            where("menu.restaurant.hashId", SearchOperationEnum.EQUAL, filter.getHashIdRestaurant());
        }

        if (ValidationUtil.isNotBlank(filter.getName())) {
            where("name", SearchOperationEnum.LIKE, filter.getName());
        }

        if (ValidationUtil.isNotBlank(filter.getDescription())) {
            where("description", SearchOperationEnum.LIKE, filter.getDescription());
        }

        if (ValidationUtil.isNotNull(filter.getAvailability())) {
            where("availability", SearchOperationEnum.EQUAL, filter.getAvailability());
        }
    }

    @Override
    protected MenuItemSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new MenuItemSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected MenuItemSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new MenuItemSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}