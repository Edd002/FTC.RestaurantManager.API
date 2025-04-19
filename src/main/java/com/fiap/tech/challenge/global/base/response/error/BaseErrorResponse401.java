package com.fiap.tech.challenge.global.base.response.error;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public final class BaseErrorResponse401 extends BaseErrorResponse {

	public BaseErrorResponse401(List<String> messages) {
		super(HttpStatus.UNAUTHORIZED.value(), messages);
	}

	@Schema(description = "Se a requisição foi bem sucedida.", example = "false")
	public boolean isSuccess() {
		return success;
	}

	@Schema(description = "Erro da resposta.", example = "Unauthorized")
	public String getError() {
		return error;
	}

	@Schema(description = "Mensagens da requisição.", type = "List", example = "[\"A autenticação deve ser realizada para obter a resposta solicitada.\"]")
	public List<String> getMessages() {
		return messages;
	}

	@Schema(description = "Código de status.", example = "401")
	public int getStatus() {
		return this.status;
	}

	@Schema(description = "URI do endpoint.", example = "/cuc/api/v1/path")
	public String getPath() {
		return this.path;
	}

	@Schema(description = "Causa da resposta.", example = "Unauthorized")
	public String getReason() {
		return this.reason;
	}

	@Schema(description = "Data e hora da resposta.", example = "2023-09-26 16:42:12.147", type = "string", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
	public Date getTimestamp() {
		return this.timestamp;
	}
}