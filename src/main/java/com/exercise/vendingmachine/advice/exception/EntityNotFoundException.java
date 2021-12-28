package com.exercise.vendingmachine.advice.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String param) {
        super(param);
    }
}
