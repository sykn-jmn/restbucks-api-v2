package com.orangeandbronze.restbucks.orders;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class OrderNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus
    String orderNotFoundHandler(OrderNotFoundException e){
        return e.getMessage();
    }
}