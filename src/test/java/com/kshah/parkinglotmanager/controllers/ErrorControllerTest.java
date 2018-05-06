package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.Error;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(MockitoJUnitRunner.class)
public class ErrorControllerTest {

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;

    @Mock
    private BindingResult bindingResult;

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
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Specified content type is not supported", responseEntity.getBody().getDescription());
        assertEquals(exceptionMessage, responseEntity.getBody().getErrors().get(0).getError());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testHttpMessageNotReadable() throws Exception {
        String exceptionMessage = "This is the exception message";
        HttpMessageNotReadableException e = new HttpMessageNotReadableException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.httpMessageUnreadable(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Malformed request", responseEntity.getBody().getDescription());
        assertNotNull(responseEntity.getBody().getErrors());
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
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Internal server error", responseEntity.getBody().getDescription());
        assertNotNull(responseEntity.getBody().getErrors());
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


    @Test
    public void testResourceNotFound() throws Exception {
        String exceptionMessage = "This is the exception message";
        ResourceNotFoundException e = new ResourceNotFoundException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.resourceNotFound(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.NOT_FOUND.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Resource not found", responseEntity.getBody().getDescription());
        assertEquals(exceptionMessage, responseEntity.getBody().getErrors().get(0).getError());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testValidationError_MethodArgumentNotValidException() {
        String message = "This is a message";
        ObjectError objectError = new ObjectError("", message);
        Mockito.when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(objectError));
        Mockito.when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
        Mockito.when(methodArgumentNotValidException.getMessage()).thenReturn(message);

        ResponseEntity<Error> responseEntity = errorController.validationError(methodArgumentNotValidException);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Validation failed on request", responseEntity.getBody().getDescription());
        assertEquals(message, responseEntity.getBody().getErrors().get(0).getError());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


    @Test
    public void testValidationError_BadDataException() throws Exception {
        String exceptionMessage = "This is the exception message";
        BadDataException e = new BadDataException(exceptionMessage);

        ResponseEntity<Error> responseEntity = errorController.validationError(e);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertEquals(Error.class, responseEntity.getBody().getClass());
        assertEquals(HttpStatus.BAD_REQUEST.value(), (int) responseEntity.getBody().getStatus());
        assertEquals("Validation failed on request", responseEntity.getBody().getDescription());
        assertEquals(exceptionMessage, responseEntity.getBody().getErrors().get(0).getError());
        assertNotNull(responseEntity.getBody().getTimestamp());
    }


}