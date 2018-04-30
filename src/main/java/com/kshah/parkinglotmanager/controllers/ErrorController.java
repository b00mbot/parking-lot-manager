package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@ControllerAdvice(annotations = RestController.class)
public class ErrorController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFound(final ResourceNotFoundException e) {
        return createErrorResponse(e, "The resource requested was not found", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<Error> badData(final BadDataException e) {
        return createErrorResponse(e, "The request sent contained invalid data", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ParkingLotCapacityReachedException.class)
    public ResponseEntity parkingLotCapacityReached(final ParkingLotCapacityReachedException e) {
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> internalServerError(final Exception e) {
        return createErrorResponse(e, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Error> createErrorResponse(final Exception e, String userMessage, HttpStatus httpStatus) {

        Error error = new Error();
        error.setStatus(httpStatus.value());
        error.setDeveloperMessage(e.getMessage());
        error.setUserMessage(userMessage);
        error.setTimestamp(Instant.now());

        return new ResponseEntity<Error>(error, httpStatus);
    }

}
