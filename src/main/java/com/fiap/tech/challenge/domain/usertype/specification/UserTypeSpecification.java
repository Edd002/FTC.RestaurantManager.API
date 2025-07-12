package com.fiap.tech.challenge.domain.usertype.specification;

import com.fiap.tech.challenge.domain.usertype.entity.UserType;
import com.fiap.tech.challenge.global.search.specification.BasicSpecification;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class UserTypeSpecification extends BasicSpecification<UserType> implements Specification<UserType> {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserTypeSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<UserType> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        return super.genericPredicate(root, builder);
    }
}