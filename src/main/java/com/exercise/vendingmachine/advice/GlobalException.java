package com.exercise.vendingmachine.advice;

import com.exercise.vendingmachine.advice.exception.AccessDeniedException;
import com.exercise.vendingmachine.advice.exception.EntityNotFoundException;
import com.exercise.vendingmachine.advice.exception.UsernameNotFoundException;
import com.exercise.vendingmachine.config.FilterConfig;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.Filter;

@Slf4j
@ControllerAdvice
public class GlobalException {

    @Autowired
    FilterConfig filterConfig;

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleAccessDeniedException(AccessDeniedException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.FORBIDDEN.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());
        log.error("Access Denied Exception", FilterConfig.IP_ADDRESS,FilterConfig.URL_ADDRESS,FilterConfig.SESSION_ID,FilterConfig.USER_AGENT);
        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleEntityNotFoundException(EntityNotFoundException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.error("Access Denied Exception");

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorObject> handleUsernameNotFoundException(UsernameNotFoundException exception) {

        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.error("Username Not Found Exception");

        return new ResponseEntity<ErrorObject>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorObject> handleNoHandlerFound(NoHandlerFoundException exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setStatus(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(exception.getMessage());
        errorObject.setTimestamp(System.currentTimeMillis());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.error("No Handler Found Exception");

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObject> handle(Exception exception) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setTimestamp(System.currentTimeMillis());
        errorObject.setMessage(exception.getMessage());

        if (exception instanceof NullPointerException) {
            errorObject.setStatus(HttpStatus.BAD_REQUEST.value());

            MDC.put("ip", FilterConfig.IP_ADDRESS);
            MDC.put("url", FilterConfig.URL_ADDRESS );
            MDC.put("session",FilterConfig.SESSION_ID);
            MDC.put("agent",FilterConfig.USER_AGENT);
            log.error("Bad request");

            return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.BAD_REQUEST);
        }

        errorObject.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        MDC.put("ip", FilterConfig.IP_ADDRESS);
        MDC.put("url", FilterConfig.URL_ADDRESS );
        MDC.put("session",FilterConfig.SESSION_ID);
        MDC.put("agent",FilterConfig.USER_AGENT);
        log.error("Internal Server Error");

        return new ResponseEntity<ErrorObject>(errorObject,HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
