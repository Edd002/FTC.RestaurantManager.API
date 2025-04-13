package com.fiap.tech.challenge.global.base.success.nocontent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.success.BaseSuccessResponse200;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public final class NoPayloadSuccessResponse200<T> extends BaseSuccessResponse200<T> {

	public NoPayloadSuccessResponse200() {
		super();
	}

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}
}