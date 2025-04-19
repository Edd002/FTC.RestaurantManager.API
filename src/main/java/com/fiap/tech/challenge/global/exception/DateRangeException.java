package com.fiap.tech.challenge.global.exception;

import java.io.Serial;

public class DateRangeException extends ApiException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DateRangeException(String actualDate, String beginDate, String endDate) {
        super(String.format("A data %s não está em um intervalo de datas aceito. Informe uma data entre %s e %s.", actualDate, beginDate, endDate));
    }
}