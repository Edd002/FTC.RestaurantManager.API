package com.fiap.tech.challenge.global.exception;

import com.fiap.tech.challenge.global.base.BaseErrorResponse;
import com.fiap.tech.challenge.global.base.response.error.BaseErrorResponse400;

import java.io.Serial;
import java.util.List;

public final class DateRangeException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DateRangeException(String actualDate, String beginDate, String endDate) {
        super(String.format("A data %s não está em um intervalo de datas aceito. Informe uma data entre %s e %s.", actualDate, beginDate, endDate));
    }

    @Override
    public BaseErrorResponse getBaseErrorResponse() {
        return new BaseErrorResponse400(List.of(super.getMessage()));
    }
}