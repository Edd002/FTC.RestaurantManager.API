package com.fiap.tech.challenge.domain.order.specification;

import com.fiap.tech.challenge.domain.order.entity.Order;
import com.fiap.tech.challenge.global.search.specification.BasicSpecification;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class OrderSpecification extends BasicSpecification<Order> implements Specification<Order> {

    @Serial
    private static final long serialVersionUID = 1L;

    public OrderSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<Order> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        return super.genericPredicate(root, builder);
    }
}