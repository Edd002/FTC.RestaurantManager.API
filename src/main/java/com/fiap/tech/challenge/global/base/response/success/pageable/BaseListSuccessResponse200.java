package com.fiap.tech.challenge.global.base.response.success.pageable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fiap.tech.challenge.global.base.BaseSuccessResponse;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class BaseListSuccessResponse200<T extends BaseResponseDTO> extends BaseSuccessResponse<T> {

	@Schema(description = "Lista de todos os objetos de resposta.", type = "List")
	@JsonProperty("list")
	private Collection<T> list = new ArrayList<>();

	public BaseListSuccessResponse200(int status) {
		super(HttpStatus.OK.value());
	}

	@Override
	public ResponseEntity<BaseListSuccessResponse200<T>> buildResponse() {
		return new ResponseEntity<>(this, HttpStatus.valueOf(this.status));
	}
}