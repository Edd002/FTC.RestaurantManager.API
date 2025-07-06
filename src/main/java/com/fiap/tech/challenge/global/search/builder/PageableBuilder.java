package com.fiap.tech.challenge.global.search.builder;

import com.fiap.tech.challenge.global.base.BasePaginationFilter;
import com.fiap.tech.challenge.global.util.ValidationUtil;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PageableBuilder {

	public Pageable build(BasePaginationFilter filter) {
		int pageNumber = ValidationUtil.isNotNull(filter.getPageNumber()) ? (filter.getPageNumber() - NumberUtils.INTEGER_ONE) : NumberUtils.INTEGER_ZERO;
		int pageSize = filter.getAll() || ValidationUtil.isNull(filter.getPageSize()) ? Integer.MAX_VALUE : filter.getPageSize();
		List<String> sortBy = filter.getSortBy();
		List<Sort.Order> sortFields = ValidationUtil.isNotEmpty(sortBy) ? orderProperties(sortBy) : new ArrayList<>();
		return Optional.ofNullable(filter.getSortDirection()).map(sortDirection ->
				switch (sortDirection) {
					case ASC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields));
					case DESC -> PageRequest.of(pageNumber, pageSize, Sort.by(sortFields).descending());
				}
		).orElseGet(() -> PageRequest.of(pageNumber, pageSize));
	}

	private List<Sort.Order> orderProperties(List<String> properties) {
		List<Sort.Order> sortFields = new ArrayList<>();
		properties.forEach(property -> sortFields.add(Sort.Order.by(property)));
		return sortFields;
	}
}