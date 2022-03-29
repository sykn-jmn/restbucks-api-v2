package com.orangeandbronze.restbucks.drinks;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class DrinkNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DrinkNotFoundException.class)
    @ResponseStatus
    String drinkNotFoundHandler(DrinkNotFoundException e){
        return e.getMessage();
    }
}
