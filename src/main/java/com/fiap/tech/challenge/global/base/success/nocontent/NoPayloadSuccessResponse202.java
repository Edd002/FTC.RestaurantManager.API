package com.fiap.tech.challenge.global.base.success.nocontent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.success.BaseSuccessResponse202;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public final class NoPayloadSuccessResponse202<T> extends BaseSuccessResponse202<T> {

	public NoPayloadSuccessResponse202() {
		super();
	}

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}
}