package com.dekel.darwin.users.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.client.WebClientResponseException.NotFound;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleExpectedException(Exception exception) {
        return ResponseEntity.badRequest()
                .body(exception.getMessage());
    }

    @ExceptionHandler({NotFound.class, WebClientResponseException.class})
    public ResponseEntity<?> handleUnexpectedException(Exception exception) {
        LOGGER.error("got unexpected error", exception);
        return handleExpectedException(exception);
    }

}
