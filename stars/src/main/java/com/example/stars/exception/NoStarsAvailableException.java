package com.example.stars.exception;

public class NoStarsAvailableException extends RuntimeException {
    public NoStarsAvailableException(String message) {
        super(message);
    }
}
