package com.fiap.tech.challenge.global.base.success;

import com.fiap.tech.challenge.global.base.BaseSuccessResponse;
import com.fiap.tech.challenge.global.base.dto.BaseResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;

public class BaseSuccessResponse200<T extends BaseResponseDTO> extends BaseSuccessResponse<T> {

	protected BaseSuccessResponse200() {
		super(HttpStatus.OK.value());
	}

	public BaseSuccessResponse200(T item) {
		super(HttpStatus.OK.value(), item);
	}

	@Schema(description = "Se a requisição foi bem sucedida.", example = "true")
	public boolean isSuccess() {
		return success;
	}

	@Schema
	public T getItem() {
		return this.item;
	}

	@Schema(description = "Código de status.", example = "200")
	public int getStatus() {
		return this.status;
	}

	@Schema(description = "URI do endpoint.", example = "/cuc/api/v1/path")
	public String getPath() {
		return this.path;
	}

	@Schema(description = "Causa da resposta.", example = "OK")
	public String getReason() {
		return this.reason;
	}

	@Schema(description = "Data e hora da resposta.", example = "2023-09-26 16:42:12.147", type = "string", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getTimestamp() {
		return this.timestamp;
	}

	@Override
	public ResponseEntity<BaseSuccessResponse200<T>> getResponse() {
		return new ResponseEntity<>(this, HttpStatus.valueOf(this.status));
	}
}