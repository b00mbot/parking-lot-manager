package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ErrorController {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFound(final ResourceNotFoundException e) {
        log.error("The requested resource was not found.", e);
        return createErrorResponse(e, "The requested resource was not found", HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<Error> badData(final BadDataException e) {
        log.error("Invalid data was provided in the request.", e);
        return createErrorResponse(e, "Invalid data was provided in the request", HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ParkingLotCapacityReachedException.class)
    public ResponseEntity parkingLotCapacityReached(final ParkingLotCapacityReachedException e) {
        log.error("Parking lot capacity has been reached.", e);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> internalServerError(final Exception e) {
        log.error("An internal server error occurred.", e);
        return createErrorResponse(e, "An internal server error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Error> createErrorResponse(final Exception e, String userMessage, HttpStatus httpStatus) {

        Error error = new Error();
        error.setStatus(httpStatus.value());
        error.setDeveloperMessage(e.getMessage());
        error.setUserMessage(userMessage);
        error.setTimestamp(Instant.now());

        return new ResponseEntity<>(error, httpStatus);
    }

}
