package com.fiap.tech.challenge.global.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
public class BaseListResponse<T> {

	@Schema(description = "Lista de todos os objetos de resposta.", type = "List")
	@JsonProperty("list")
	private Collection<T> list = new ArrayList<>();
}