package com.fiap.tech.challenge.domain.city.specification;

import com.fiap.tech.challenge.domain.city.City;
import com.fiap.tech.challenge.domain.city.dto.CityGetFilter;
import com.fiap.tech.challenge.global.entity.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.entity.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.entity.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.entity.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
public class CitySpecificationBuilder extends BasicSpecificationBuilder<City, CitySpecification, CityGetFilter> {

    @Override
    protected void initParams(CityGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getName())) {
            where("name", SearchOperationEnum.LIKE, filter.getName());
        }

        if (ValidationUtil.isNotBlank(filter.getUfState())) {
            where("state.uf", SearchOperationEnum.EQUAL_IGNORE_CASE, filter.getUfState());
        }
    }

    @Override
    protected CitySpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new CitySpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected CitySpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new CitySpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}