package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class ErrorControllerTest {


    private ErrorController errorController;

    @Before
    public void setUp() throws Exception {
        errorController = new ErrorController();
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
        assertEquals("The resource requested was not found", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testBadData() throws Exception {
        String exceptionMessage = "This is the exception message";
        BadDataException e = new BadDataException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.badData(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int)responseEntity.getBody().getStatus());
        assertEquals(exceptionMessage, responseEntity.getBody().getDeveloperMessage());
        assertEquals("The request sent contained invalid data", responseEntity.getBody().getUserMessage());
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
        assertEquals("Internal server error", responseEntity.getBody().getUserMessage());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }



}