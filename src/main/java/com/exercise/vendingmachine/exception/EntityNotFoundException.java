package com.exercise.vendingmachine.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String param) {
        super(param);
    }
}
