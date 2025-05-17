package com.fiap.tech.challenge.global.base.response.error;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public final class BaseErrorResponse412 extends BaseErrorResponse {

    public BaseErrorResponse412(List<String> messages) {
        super(HttpStatus.PRECONDITION_FAILED.value(), messages);
    }

    @Schema(description = "Se a requisição foi bem sucedida.", example = "false")
    public boolean isSuccess() {
        return success;
    }

    @Schema(description = "Erro da resposta.", example = "Precondition Failed")
    public String getError() {
        return error;
    }

    @Schema(description = "Mensagens da requisição.", type = "List", example = "[\"Não foi possível atender as pré-condições solicitadas.\"]")
    public List<String> getMessages() {
        return messages;
    }

    @Schema(description = "Código de status.", example = "412")
    public int getStatus() {
        return this.status;
    }

    @Schema(description = "URI do endpoint.", example = "/restaurant-manager/api/v1/path")
    public String getPath() {
        return this.path;
    }

    @Schema(description = "Causa da resposta.", example = "Precondition Failed")
    public String getReason() {
        return this.reason;
    }

    @Schema(description = "Data e hora da resposta.", example = "2025-04-26 16:42:12.147", type = "string", pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    public Date getTimestamp() {
        return this.timestamp;
    }
}