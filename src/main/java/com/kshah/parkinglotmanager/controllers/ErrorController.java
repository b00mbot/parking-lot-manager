package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import com.kshah.parkinglotmanager.model.api.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
public class ErrorController {

    public static final String RESOURCE_NOT_FOUND_MSG = "Resource not found";
    public static final String CONTENT_TYPE_UNSUPPORTED_MSG = "Specified content type is not supported";
    public static final String VALIDATION_ERROR_MSG = "Validation failed on request";
    public static final String HTTP_MESSAGE_UNREADABLE_MSG = "Malformed request";
    public static final String INTERNAL_SERVER_ERROR_MSG = "Internal server error";


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<Error> contentTypeNotSupported(final HttpMediaTypeNotSupportedException e) {
        log.error("Content type not supported in request body.", e);
        return createErrorResponse(HttpStatus.UNSUPPORTED_MEDIA_TYPE, CONTENT_TYPE_UNSUPPORTED_MSG, Collections.singletonList(e.getMessage()));
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Error> httpMessageUnreadable(final HttpMessageNotReadableException e) {
        log.error("HTTP message is unreadable", e);
        return createErrorResponse(HttpStatus.BAD_REQUEST, HTTP_MESSAGE_UNREADABLE_MSG);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> internalServerError(final Exception e) {
        log.error("An internal server error occurred.", e);
        return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
    }


    @ExceptionHandler(ParkingLotCapacityReachedException.class)
    public ResponseEntity parkingLotCapacityReached(final ParkingLotCapacityReachedException e) {
        log.error("Parking lot capacity has been reached.", e);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Error> resourceNotFound(final ResourceNotFoundException e) {
        log.error("The requested resource was not found.", e);
        return createErrorResponse(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND_MSG, Collections.singletonList(e.getMessage()));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> validationError(final MethodArgumentNotValidException e) {
        log.error("Validation error occurred on request.", e);
        List<String> errors = new ArrayList<>();
        List<ObjectError> validationErrors = e.getBindingResult().getAllErrors();
        for (int i = 0; i < validationErrors.size(); i++) {
            errors.add(validationErrors.get(i).getDefaultMessage());
        }
        return createErrorResponse(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MSG, errors);
    }


    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<Error> validationError(final BadDataException e) {
        log.error("Validation error occurred on request.", e);
        return createErrorResponse(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MSG, Collections.singletonList(e.getMessage()));
    }


    private ResponseEntity<Error> createErrorResponse(HttpStatus httpStatus, String description) {
        return createErrorResponse(httpStatus, description, null);
    }


    private ResponseEntity<Error> createErrorResponse(HttpStatus httpStatus, String description, List<String> errorDetails) {
        Error error = new Error();
        error.setStatus(httpStatus.value());
        error.setDescription(description);
        error.setTimestamp(Instant.now());

        List<ErrorDetail> errors = new ArrayList<>();
        if (errorDetails != null) {
            for (int i = 0; i < errorDetails.size(); i++) {
                ErrorDetail errorDetail = new ErrorDetail();
                errorDetail.setError(errorDetails.get(i));
                errors.add(errorDetail);
            }
        }
        error.setErrors(errors);

        return new ResponseEntity<>(error, httpStatus);
    }

}
