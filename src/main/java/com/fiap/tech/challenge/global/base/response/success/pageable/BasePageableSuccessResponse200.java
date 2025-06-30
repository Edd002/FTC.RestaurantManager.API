package com.fiap.tech.challenge.global.base.response.success.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

@Getter
@Setter
public class BasePageableSuccessResponse200<T extends BaseResponseDTO> extends BaseSuccessResponse200<T> {

	@Schema(description = "Número da página.", example = "1", defaultValue = "0")
	private Integer pageNumber;

	@Schema(description = "Tamanho da página.", example = "50", defaultValue = "0")
	private Integer pageSize;

	@Schema(description = "Total de elementos.", example = "100", defaultValue = "0")
	private Long totalElements;

	@Schema(description = "Lista de todos os objetos de resposta.", type = "List")
	private Collection<T> list;

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}

	public BasePageableSuccessResponse200(Page<T> page) {
		super();
		this.pageNumber = page.getPageable().getPageNumber() + NumberUtils.INTEGER_ONE;
		this.pageSize = page.getPageable().getPageSize();
		this.list = page.getContent();
		this.totalElements = page.getTotalElements();
	}

	public ResponseEntity<BasePageableSuccessResponse200<T>> buildPageableResponse() {
		return new ResponseEntity<>(this, HttpStatus.valueOf(this.status));
	}
}