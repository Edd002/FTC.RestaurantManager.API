package com.fiap.tech.challenge.global.base.success.nocontent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.success.BaseSuccessResponse204;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public final class NoPayloadSuccessResponse204<T> extends BaseSuccessResponse204<T> {

	public NoPayloadSuccessResponse204() {
		super();
	}

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}
}