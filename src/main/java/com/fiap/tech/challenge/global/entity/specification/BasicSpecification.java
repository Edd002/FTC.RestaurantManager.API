package com.fiap.tech.challenge.global.entity.specification;

import com.fiap.tech.challenge.global.entity.enumerated.SearchOperationEnum;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

public final class BasicSpecification<TYPE> {

    private final transient SearchCriteria criteria;

    public BasicSpecification(SearchCriteria searchCriteria) {
        this.criteria = searchCriteria;
    }

    private Predicate genericPredicate(Root<TYPE> root, CriteriaBuilder builder) {
        boolean isChild = ValidationUtil.isNotBlank(criteria.getKey()) && criteria.getKey().contains(".");

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.EQUAL) && criteria.getValue() instanceof Date) {
            String[] params = criteria.getKey().split("\\.");
            return builder.equal(
                    builder.function("date", Date.class, root.join(params[0]).get(params[1])),
                    builder.function("date", Date.class, builder.literal(criteria.getValue())));
        }

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.EQUAL)) {
            String[] params = criteria.getKey().split("\\.");
            return builder.equal(root.join(params[0]).get(params[1]), criteria.getValue());
        }

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.LIKE)) {
            String[] params = criteria.getKey().split("\\.");
            return builder.like(
                    builder.lower(root.join(params[0]).get(params[1])),
                    "%" + StringUtils.normalizeSpace(String.valueOf(criteria.getValue())).toLowerCase() + "%"
            );
        }

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.BETWEEN) && criteria.getValue() instanceof Date) {
            String[] params = criteria.getKey().split("\\.");
            return builder.between(
                    builder.function("date", Date.class, root.join(params[0]).get(params[1])),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getValue())),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getParam())));
        }

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.IN)) {
            String[] params = criteria.getKey().split("\\.");
            return root.join(params[0]).get(params[1]).in((Collection<?>) criteria.getValue());
        }

        if (isChild && criteria.getOperation().equals(SearchOperationEnum.IN_JOIN)) {
            String[] params = criteria.getKey().split("\\.");
            return root.join(params[0]).join(params[1]).in((Collection<?>) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.EQUAL) && criteria.getValue() instanceof Date) {
            return builder.equal(
                    builder.function("date", Date.class, root.get(criteria.getKey())),
                    builder.function("date", Date.class, builder.literal(criteria.getValue())));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.EQUAL_IGNORE_CASE)) {
            return builder.like(builder.lower(root.get(criteria.getKey())), StringUtils.normalizeSpace(String.valueOf(criteria.getValue())).toLowerCase());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.EQUAL)) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.NOT_EQUAL) && criteria.getValue() instanceof Date) {
            return builder.notEqual(
                    builder.function("date", Date.class, root.get(criteria.getKey())),
                    builder.function("date", Date.class, builder.literal(criteria.getValue())));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.NOT_EQUAL)) {
            return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.IS_NULL)) {
            return builder.isNull(root.get(criteria.getKey()));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.IS_NOT_NULL)) {
            return builder.isNotNull(root.get(criteria.getKey()));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.LIKE)) {
            return builder.like(builder.lower(root.get(criteria.getKey())), "%" + StringUtils.normalizeSpace(String.valueOf(criteria.getValue())).toLowerCase() + "%");
        }

        if (criteria.getOperation().equals(SearchOperationEnum.GREATEST) && criteria.getValue() instanceof Date) {
            return builder.greaterThanOrEqualTo(
                    builder.function("date", Date.class, root.get(criteria.getKey())),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getValue())));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.GREATEST) && criteria.getValue() instanceof Long) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Long) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.GREATEST) && criteria.getValue() instanceof BigDecimal) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (BigDecimal) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.LEAST) && criteria.getValue() instanceof Date) {
            return builder.lessThanOrEqualTo(
                    builder.function("date", Date.class, root.get(criteria.getKey())),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getValue())));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.LEAST) && criteria.getValue() instanceof Long) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Long) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.LEAST) && criteria.getValue() instanceof BigDecimal) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (BigDecimal) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.BETWEEN) && criteria.getValue() instanceof Date) {
            return builder.between(
                    builder.function("date", Date.class, root.get(criteria.getKey())),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getValue())),
                    builder.function("date", Date.class, builder.literal((Date) criteria.getParam())));
        }

        if (criteria.getOperation().equals(SearchOperationEnum.BETWEEN) && criteria.getValue() instanceof Long) {
            return builder.between(root.get(criteria.getKey()), (Long) criteria.getValue(), (Long) criteria.getParam());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.BETWEEN) && criteria.getValue() instanceof BigDecimal) {
            return builder.between(root.get(criteria.getKey()), (BigDecimal) criteria.getValue(), (BigDecimal) criteria.getParam());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.IN)) {
            return root.get(criteria.getKey()).in((Collection<?>) criteria.getValue());
        }

        if (criteria.getOperation().equals(SearchOperationEnum.IN_JOIN)) {
            return root.join(criteria.getKey()).in((Collection<?>) criteria.getValue());
        }

        return null;
    }
}