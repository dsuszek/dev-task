package com.example.stars.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * Handles NoStarsAvailableException and returns a 400 Bad Request response.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity with the error message and 400 status
     */
    @ExceptionHandler(NoStarsAvailableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleNoStarsAvailableException(NoStarsAvailableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    /**
     * Handles StarNotFoundException and returns a 404 Not Found response.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity with the error message and 404 status
     */
    @ExceptionHandler(StarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleStarNotFoundException(StarNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    /**
     * Handles general exceptions and returns a 500 Internal Server Error response.
     *
     * @param ex the exception thrown
     * @return a ResponseEntity with the error message and 500 status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }
}
