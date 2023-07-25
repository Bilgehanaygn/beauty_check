package com.example.demo.ortak.configuration;

import com.example.demo.ortak.messages.MessageResponse;
import com.example.demo.ortak.messages.MessageType;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(0)
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleEntityNotFoundException(EntityNotFoundException entityNotFoundException){
        entityNotFoundException.printStackTrace();
        return new MessageResponse("Item not found.", MessageType.ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleException(Exception exception){
        exception.printStackTrace();
        return new MessageResponse("Unknown error occurred.", MessageType.ERROR);
    }
}
