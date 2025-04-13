package com.fiap.tech.challenge.global.base.success.nocontent;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fiap.tech.challenge.global.base.success.BaseSuccessResponse201;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

public final class NoPayloadSuccessResponse201<T> extends BaseSuccessResponse201<T> {

	public NoPayloadSuccessResponse201() {
		super();
	}

	@Schema
	@Hidden
	@JsonIgnore
	public T getItem() {
		return this.item;
	}
}