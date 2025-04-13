package com.fiap.tech.challenge.global.base.success.nocontent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import com.fiap.tech.challenge.global.base.success.BaseSuccessResponse202;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class NoPayloadBaseSuccessResponse202<T extends BaseResponseDTO> extends BaseSuccessResponse202<T> {

	public NoPayloadBaseSuccessResponse202() {
		super();
	}

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}

	@Override
	public ResponseEntity<NoPayloadBaseSuccessResponse202<T>> getResponseWithoutPayload() {
		return new ResponseEntity<>(HttpStatus.valueOf(this.status));
	}
}