package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.Capacity;
import com.kshah.parkinglotmanager.services.CapacityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Instant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ParkingCapacityControllerTest {

    @Mock
    private CapacityService capacityService;

    private ParkingCapacityController parkingCapacityController;


    @Before
    public void setUp() throws Exception {
        parkingCapacityController = new ParkingCapacityController(capacityService);
    }

    @Test
    public void getParkingLotCapacity() {
        Capacity capacity = new Capacity();
        capacity.setCurrent(0);
        capacity.setLastModified(Instant.now());
        capacity.setMax(10);
        Mockito.when(capacityService.getCapacity()).thenReturn(capacity);

        ResponseEntity responseEntity = parkingCapacityController.getParkingLotCapacity();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Capacity.class, responseEntity.getBody().getClass());
        assertEquals(capacity.getCurrent(), ((Capacity) responseEntity.getBody()).getCurrent());
        assertEquals(capacity.getLastModified(), ((Capacity) responseEntity.getBody()).getLastModified());
        assertEquals(capacity.getMax(), ((Capacity) responseEntity.getBody()).getMax());
    }

}