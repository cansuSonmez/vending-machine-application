package com.exercise.vendingmachine.advice;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorObject {

    private int status;

    private String message;

    private Long timestamp;

}
