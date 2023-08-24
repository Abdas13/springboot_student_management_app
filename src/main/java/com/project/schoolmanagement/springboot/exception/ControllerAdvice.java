package com.project.schoolmanagement.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private Error error;

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Error> resourceNotFoundException(ResourceNotFoundException ex) {
        error = new Error();

        error.setMessage(ex.getMessage());
        error.setStatusCode(404);

        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<Error> alreadyExists(ConflictException ex){
        error = new Error();

        error.setMessage(ex.getMessage());
        error.setStatusCode(409);
        return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
    }
}
