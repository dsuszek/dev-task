package com.example.stars.exception;

public class StarNotFoundException extends RuntimeException {
    public StarNotFoundException(String message) {
        super(message);
    }
}
