package com.exercise.vendingmachine.advice.exception;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException(String param){
        super(param);
    }
}
