package com.exercise.vendingmachine.advice.exception;

public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException(String param) {
        super(param);
    }

}
