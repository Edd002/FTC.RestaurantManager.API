package com.fiap.tech.challenge.global.base;

import com.fiap.tech.challenge.global.util.ValidationUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseErrorResponse extends BaseResponse {

	protected String error;

	protected List<String> messages;

	public BaseErrorResponse(int status, List<String> messages) {
		super(status);
		this.success = false;
		this.error = HttpStatus.valueOf(status).getReasonPhrase();
		this.messages = ValidationUtil.isNotNull(messages) ? messages : new ArrayList<>();
	}

	public BaseErrorResponse addMessage(String message) {
		this.messages.add(message);
		return this;
	}
}