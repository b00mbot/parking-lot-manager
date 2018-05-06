package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.fixtures.RestAPITestFixtures;
import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.services.GateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ParkingGateControllerTest {

    @Mock
    private GateService gateService;

    private ParkingGateController parkingGateController;


    @Before
    public void setUp() throws Exception {
        parkingGateController = new ParkingGateController(gateService);
    }


    @Test
    public void getGateByID() {

        Gate gate = RestAPITestFixtures.createGate("1", OperationStatus.OPERATIONAL);
        Mockito.when(gateService.getGate("1")).thenReturn(gate);

        ResponseEntity responseEntity = parkingGateController.getGateByID("1");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Gate.class, responseEntity.getBody().getClass());
        assertEquals(gate.getId(), ((Gate) responseEntity.getBody()).getId());
        assertEquals(gate.getCreated(), ((Gate) responseEntity.getBody()).getCreated());
        assertEquals(gate.getLastModified(), ((Gate) responseEntity.getBody()).getLastModified());
        assertEquals(gate.getStatus(), ((Gate) responseEntity.getBody()).getStatus());
    }


    @Test
    public void getAllGates() {

        Gate gate1 = RestAPITestFixtures.createGate("1", OperationStatus.OPERATIONAL);
        Gate gate2 = RestAPITestFixtures.createGate("2", OperationStatus.READY_FOR_OPERATION);
        List<Gate> gates = Arrays.asList(gate1, gate2);
        Mockito.when(gateService.getAllGates()).thenReturn(gates);

        ResponseEntity responseEntity = parkingGateController.getAllGates();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, ((List<Gate>) responseEntity.getBody()).size());

        List<Gate> actualGates = ((List<Gate>) responseEntity.getBody());
        assertEquals(gate1.getId(), actualGates.get(0).getId());
        assertEquals(gate1.getCreated(), actualGates.get(0).getCreated());
        assertEquals(gate1.getLastModified(), actualGates.get(0).getLastModified());
        assertEquals(gate1.getStatus(), actualGates.get(0).getStatus());
        assertEquals(gate2.getId(), actualGates.get(1).getId());
        assertEquals(gate2.getCreated(), actualGates.get(1).getCreated());
        assertEquals(gate2.getLastModified(), actualGates.get(1).getLastModified());
        assertEquals(gate2.getStatus(), actualGates.get(1).getStatus());
    }


    @Test
    public void getAllGates_Empty() {
        Mockito.when(gateService.getAllGates()).thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = parkingGateController.getAllGates();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, ((List<Gate>) responseEntity.getBody()).size());
    }


    @Test
    public void createGate() {
        Link link = RestAPITestFixtures.createGateLink("1");
        Mockito.when(gateService.createGate(Mockito.any(CreateGateRequest.class))).thenReturn(link);
        CreateGateRequest request = RestAPITestFixtures.createGateRequest(OperationStatus.OPERATIONAL, "USER", "Reason");

        ResponseEntity responseEntity = parkingGateController.createGate(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Link.class, responseEntity.getBody().getClass());
        assertEquals(link.getId(), ((Link) responseEntity.getBody()).getId());
        assertEquals(link.getLink(), ((Link) responseEntity.getBody()).getLink());
    }


    @Test
    public void updateGateByID() {
        Link link = RestAPITestFixtures.createGateLink("1");
        Mockito.when(gateService.updateGate(Mockito.anyString(), Mockito.any(UpdateGateRequest.class))).thenReturn(link);
        UpdateGateRequest request = RestAPITestFixtures.updateGateRequest(OperationStatus.OPERATIONAL, "USER", "Reason");

        ResponseEntity responseEntity = parkingGateController.updateGateByID("1", request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Link.class, responseEntity.getBody().getClass());
        assertEquals(link.getId(), ((Link) responseEntity.getBody()).getId());
        assertEquals(link.getLink(), ((Link) responseEntity.getBody()).getLink());
    }


    @Test
    public void deleteGateByID() {
        ResponseEntity responseEntity = parkingGateController.deleteGateByID("1");
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}