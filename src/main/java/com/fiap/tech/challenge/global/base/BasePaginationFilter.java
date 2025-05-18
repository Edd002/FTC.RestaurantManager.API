package com.fiap.tech.challenge.global.base;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
@Setter
public abstract class BasePaginationFilter {

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Número da página.", example = "1", defaultValue = "1")
	@Positive(message = "O número da página deve ser positivo.")
	protected int pageNumber = 1;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Tamanho da página.", example = "50", defaultValue = "50")
	@Positive(message = "O tamanho da página deve ser positivo.")
	@Max(value = 500, message = "O tamanho da página deve ser menor ou igual a 500.")
	protected int pageSize = 50;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Campos para ordenação.", type = "List", example = "[\"id\"]")
	protected List<String> sortBy;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Ordenação (crescente ou decrescente).", example = "ASC")
	protected String sortDirection;

	@Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, description = "Se todos os elementos serão buscados.", example = "false")
	protected Boolean all;

	@Hidden
	protected List<Sort.Order> sortFields;

	public BasePaginationFilter(int pageNumber, int pageSize) {
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.all = false;
	}

	public BasePaginationFilter(boolean all) {
		this.all = all;
	}
}