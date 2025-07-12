package com.fiap.tech.challenge.domain.usertype.specification;

import com.fiap.tech.challenge.domain.usertype.dto.UserTypeGetFilter;
import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
public class UserTypeSpecificationBuilder extends BasicSpecificationBuilder<UserType, UserTypeSpecification, UserTypeGetFilter> {

    @Override
    protected void initParams(UserTypeGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getName())) {
            where("name", SearchOperationEnum.LIKE, filter.getName());
        }
    }

    @Override
    protected UserTypeSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new UserTypeSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected UserTypeSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new UserTypeSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}