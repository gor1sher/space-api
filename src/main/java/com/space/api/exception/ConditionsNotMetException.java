package com.space.api.exception;

public class ConditionsNotMetException extends RuntimeException {

    public ConditionsNotMetException(String message) {
        super(message);
    }
}