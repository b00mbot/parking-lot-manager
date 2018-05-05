package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


public class ErrorControllerTest {


    private ErrorController errorController;

    @Before
    public void setUp() throws Exception {
        errorController = new ErrorController();
    }


    @Test
    public void testContentTypeNotSupported() throws Exception {
        String exceptionMessage = "This is the exception message";
        HttpMediaTypeNotSupportedException e = new HttpMediaTypeNotSupportedException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.contentTypeNotSupported(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("Content type not supported in request body", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testResourceNotFound() throws Exception {
        String exceptionMessage = "This is the exception message";
        ResourceNotFoundException e = new ResourceNotFoundException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.resourceNotFound(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.NOT_FOUND.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("The requested resource was not found", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testBadData_BadDataException() throws Exception {
        String exceptionMessage = "This is the exception message";
        BadDataException e = new BadDataException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.badData(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("Invalid data was provided in the request", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testBadData_HttpMessageNotReadableException() throws Exception {
        String exceptionMessage = "This is the exception message";
        HttpMessageNotReadableException e = new HttpMessageNotReadableException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.badData(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("Invalid data was provided in the request", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testInternalServerError() throws Exception {
        String exceptionMessage = "This is the exception message";
        Exception e = new Exception(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.internalServerError(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("An internal server error occurred", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testParkingLotCapacityReached() throws Exception {
        String exceptionMessage = "This is the exception message";
        ParkingLotCapacityReachedException e = new ParkingLotCapacityReachedException(exceptionMessage);

        ResponseEntity responseEntity = errorController.parkingLotCapacityReached(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
}