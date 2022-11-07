package com.devdojo.handler;

import exception.BadRequestException;
import exception.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRE){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Resquest Excetion, Check the Documentation")
                        .details(badRE.getMessage())
                        .developerMessage(badRE.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );

    }
}
