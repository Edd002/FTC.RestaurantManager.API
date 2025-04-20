package com.fiap.tech.challenge.global.entity.builder;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import com.fiap.tech.challenge.global.entity.enumerated.SortOrderEnum;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageableBuilder {

	public Pageable build(BasePaginationFilter filter) {
		int pageNumber = filter.getPageNumber() - 1;
		int pageSize = filter.getAll() ? Integer.MAX_VALUE : filter.getPageSize();
		List<String> sortBy = filter.getSortBy();
		List<Sort.Order> sortFields = ValidationUtil.isNotEmpty(sortBy) ? orderProperties(sortBy) : new ArrayList<>();
		SortOrderEnum sortDirection = ValidationUtil.isNull(filter.getSortDirection()) ? SortOrderEnum.EMPTY : SortOrderEnum.valueOfIgnoreCase(filter.getSortDirection());
        return switch (sortDirection) {
            case ASC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields));
            case DESC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields).descending());
            case EMPTY -> PageRequest.of(pageNumber, pageSize);
            case BOTH -> PageRequest.of(pageNumber, pageSize, Sort.by(ValidationUtil.isNotNull(filter.getSortFields()) ? filter.getSortFields() : new ArrayList<>()));
        };
	}

	private List<Sort.Order> orderProperties(List<String> properties) {
		List<Sort.Order> sortFields = new ArrayList<>();
		properties.forEach(property -> sortFields.add(Sort.Order.by(property)));
		return sortFields;
	}
}