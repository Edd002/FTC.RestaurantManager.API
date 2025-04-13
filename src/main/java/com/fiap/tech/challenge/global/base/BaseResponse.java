package com.fiap.tech.challenge.global.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Date;

@Getter
public abstract class BaseResponse {

    protected boolean success;

    protected int status;

    protected String path;

    protected String reason;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT-3")
    protected Date timestamp;

    public BaseResponse(int status) {
        this.status = status;
        this.path = ServletUriComponentsBuilder.fromCurrentRequestUri().build().getPath();
        this.reason = HttpStatus.valueOf(status).getReasonPhrase();
        this.timestamp = new Date();
    }

    public ResponseEntity<?> getResponse() {
        return new ResponseEntity<>(this, HttpStatus.valueOf(status));
    }
}