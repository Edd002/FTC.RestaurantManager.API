package com.fiap.tech.challenge.global.exception;

public final class PreConditionFailedException extends ApiException {

    public PreConditionFailedException() {
        super("Falha na condição pré estabelecida.");
    }

    public PreConditionFailedException(String message) {
        super(message);
    }
}