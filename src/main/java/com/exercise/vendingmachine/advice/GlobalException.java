package com.exercise.vendingmachine.advice;

import com.exercise.vendingmachine.exception.AccessDeniedException;
import com.exercise.vendingmachine.exception.EntityNotFoundException;
import com.exercise.vendingmachine.exception.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;


@ControllerAdvice
public class GlobalException {

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleAccessDeniedException(AccessDeniedException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.FORBIDDEN.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleUsernameNotFoundException(UsernameNotFoundException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorObject> handleNoHandlerFound(NoHandlerFoundException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handle(Exception exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(System.currentTimeMillis());
        errorObject.setMessage(exception.getMessage());

        if (exception instanceof NullPointerException) {
            errorObject.setStatus(HttpStatus.BAD_REQUEST.value());
            return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);
        }

        errorObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
