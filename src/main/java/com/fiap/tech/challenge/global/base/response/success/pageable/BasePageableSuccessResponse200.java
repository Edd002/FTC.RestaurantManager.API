package com.fiap.tech.challenge.global.base.response.success.pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import com.fiap.tech.challenge.global.base.response.success.BaseSuccessResponse200;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class BasePageableSuccessResponse200<T extends BaseResponseDTO> extends BaseSuccessResponse200<T> {

	@Schema(description = "Número da página.", example = "1", defaultValue = "0")
	private int page = 0;

	@Schema(description = "Tamanho da página.", example = "10", defaultValue = "0")
	private int offset = 0;

	@Schema(description = "Total de elementos.", example = "0", defaultValue = "0")
	private Long totalElements = 0L;

	@Schema(description = "Lista de todos os objetos de resposta.", type = "List")
	private Collection<T> list = new ArrayList<>();

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}

	public BasePageableSuccessResponse200(Pageable pageable, Page<T> page) {
		super();
		this.offset = pageable.getPageSize();
		this.page = pageable.getPageNumber();
		this.list = page.getContent();
		this.totalElements = page.getTotalElements();
	}

	public ResponseEntity<BasePageableSuccessResponse200<T>> buildPageableResponse() {
		return new ResponseEntity<>(this, HttpStatus.valueOf(this.status));
	}
}