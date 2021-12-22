package com.exercise.vendingmachine.exception;

public class UsernameNotFoundException extends RuntimeException{

    public UsernameNotFoundException(String param){
        super(param);
    }
}
