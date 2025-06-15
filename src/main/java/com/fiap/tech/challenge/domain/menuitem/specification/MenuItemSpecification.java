package com.fiap.tech.challenge.domain.menuitem.specification;

import com.fiap.tech.challenge.domain.menuitem.entity.MenuItem;
import com.fiap.tech.challenge.global.search.specification.BasicSpecification;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class MenuItemSpecification extends BasicSpecification<MenuItem> implements Specification<MenuItem> {

    @Serial
    private static final long serialVersionUID = 1L;

    public MenuItemSpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<MenuItem> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        return super.genericPredicate(root, builder);
    }
}