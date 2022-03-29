package com.orangeandbronze.restbucks.drinks;

public class DrinkNotFoundException extends RuntimeException {

    DrinkNotFoundException(Long id) {
        super("Could not find drink " + id);
    }
}