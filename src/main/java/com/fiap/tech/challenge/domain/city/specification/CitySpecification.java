package com.fiap.tech.challenge.domain.city.specification;

import com.fiap.tech.challenge.domain.city.entity.City;
import com.fiap.tech.challenge.global.search.specification.BasicSpecification;
import com.fiap.tech.challenge.global.search.specification.SearchCriteria;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class CitySpecification extends BasicSpecification<City> implements Specification<City> {

    @Serial
    private static final long serialVersionUID = 1L;

    public CitySpecification(SearchCriteria searchCriteria) {
        super(searchCriteria);
    }

    @Override
    public Predicate toPredicate(@NonNull Root<City> root, CriteriaQuery<?> query, @NonNull CriteriaBuilder builder) {
        return super.genericPredicate(root, builder);
    }
}