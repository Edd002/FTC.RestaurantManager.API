package com.fiap.tech.challenge.global.search.builder;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import com.fiap.tech.challenge.global.search.enumerated.SortOrderEnum;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PageableBuilder {

	public Pageable build(BasePaginationFilter filter) {
		int pageNumber = ValidationUtil.isNotNull(filter.getPageNumber()) ? (filter.getPageNumber() - NumberUtils.INTEGER_ONE) : NumberUtils.INTEGER_ZERO;
		int pageSize = filter.getAll() || ValidationUtil.isNull(filter.getPageSize()) ? Integer.MAX_VALUE : filter.getPageSize();
		List<String> sortBy = filter.getSortBy();
		List<Sort.Order> sortFields = ValidationUtil.isNotEmpty(sortBy) ? orderProperties(sortBy) : new ArrayList<>();
		SortOrderEnum sortDirection = ValidationUtil.isNotNull(filter.getSortDirection()) ? filter.getSortDirection() : SortOrderEnum.NONE;
        return switch (sortDirection) {
			case NONE -> PageRequest.of(pageNumber, pageSize);
			case ASC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields));
			case DESC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields).descending());
        };
	}

	private List<Sort.Order> orderProperties(List<String> properties) {
		List<Sort.Order> sortFields = new ArrayList<>();
		properties.forEach(property -> sortFields.add(Sort.Order.by(property)));
		return sortFields;
	}
}