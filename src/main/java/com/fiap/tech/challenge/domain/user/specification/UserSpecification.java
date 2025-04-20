package com.fiap.tech.challenge.domain.user.specification;

import com.fiap.tech.challenge.domain.user.User;
import com.fiap.tech.challenge.global.entity.specification.BasicSpecification;
import com.fiap.tech.challenge.global.entity.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class UserSpecification extends BasicSpecification<User> implements Specification<User> {

    @Serial
    private static final long serialVersionUID = 1L;

    public UserSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<User> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        return super.genericPredicate(root, builder);
    }
}