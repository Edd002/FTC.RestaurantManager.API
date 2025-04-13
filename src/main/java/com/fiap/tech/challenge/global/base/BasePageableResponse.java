package com.fiap.tech.challenge.global.base;

import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class BasePageableResponse<T> extends BaseResponseDTO {

	@Schema(description = "Número da página.", example = "1", defaultValue = "0")
	private int page = 0;

	@Schema(description = "Tamanho da página.", example = "10", defaultValue = "0")
	private int offset = 0;

	@Schema(description = "Total de elementos.", example = "0", defaultValue = "0")
	private Long totalElements = 0L;

	@Schema(description = "Lista de todos os objetos de resposta.", type = "List")
	private Collection<T> list = new ArrayList<>();

	public BasePageableResponse(Pageable pageable, Page<T> page) {
		this.offset = pageable.getPageSize();
		this.page = pageable.getPageNumber();
		this.list = page.getContent();
		this.totalElements = page.getTotalElements();
	}
}