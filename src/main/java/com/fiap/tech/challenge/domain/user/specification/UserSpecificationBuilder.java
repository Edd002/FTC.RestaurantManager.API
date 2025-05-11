package com.fiap.tech.challenge.domain.user.specification;

import com.fiap.tech.challenge.domain.user.entity.User;
import com.fiap.tech.challenge.domain.user.dto.UserGetFilter;
import com.fiap.tech.challenge.global.search.enumerated.FetchDeletedEnum;
import com.fiap.tech.challenge.global.search.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.search.specification.BasicSpecificationBuilder;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.stereotype.Component;

@Component
public class UserSpecificationBuilder extends BasicSpecificationBuilder<User, UserSpecification, UserGetFilter> {

    @Override
    protected void initParams(UserGetFilter filter) {
        if (ValidationUtil.isNotBlank(filter.getName())) {
            where("name", SearchOperationEnum.LIKE, filter.getName());
        }

        if (ValidationUtil.isNotBlank(filter.getEmail())) {
            where("email", SearchOperationEnum.LIKE, filter.getEmail());
        }
    }

    @Override
    protected UserSpecification buildSpecification(String key, SearchOperationEnum operation, Object value) {
        return new UserSpecification(new SearchCriteria(key, operation, value));
    }

    @Override
    protected UserSpecification buildSpecification(String key, SearchOperationEnum operation, Object value, Object param) {
        return new UserSpecification(new SearchCriteria(key, operation, value, param));
    }

    @Override
    protected FetchDeletedEnum showDeleted() {
        return FetchDeletedEnum.FETCH_ALL;
    }
}