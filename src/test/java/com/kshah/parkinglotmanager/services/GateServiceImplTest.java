package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.repositories.GateRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class GateServiceImplTest {

    @Mock
    private GateRepository gateRepository;

    private GateServiceImpl gateService;

    @Before
    public void setUp() throws Exception {
        gateService = new GateServiceImpl(gateRepository);
    }

    @Test
    public void testGetGate() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);

        Gate actualGate = gateService.getGate("1");

        assertNotNull(actualGate);
        assertEquals("1", actualGate.getId());
        assertEquals(dbGate.getCreated().toInstant(), actualGate.getCreated());
        assertEquals(dbGate.getLastModified().toInstant(), actualGate.getLastModified());
        assertEquals(dbGate.getStatus(), actualGate.getStatus());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testGetGate_ResourceNotFound() throws Exception {

        Mockito.when(gateRepository.findOne(2L)).thenReturn(null);

        gateService.getGate("2");
    }


    @Test
    public void testGetAllGates() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");
        DBGate dbGate2 = createDBGate(2L, OperationStatus.OPERATIONAL, "SYSTEM2", "SYSTEM2", "ABC");

        Mockito.when(gateRepository.findAll()).thenReturn(Arrays.asList(dbGate, dbGate2));

        List<Gate> gates = gateService.getAllGates();

        assertNotNull(gates);
        assertEquals(2, gates.size());

        assertEquals("1", gates.get(0).getId());
        assertEquals(dbGate.getCreated().toInstant(), gates.get(0).getCreated());
        assertEquals(dbGate.getLastModified().toInstant(), gates.get(0).getLastModified());
        assertEquals(dbGate.getStatus(), gates.get(0).getStatus());

        assertEquals("2", gates.get(1).getId());
        assertEquals(dbGate2.getCreated().toInstant(), gates.get(1).getCreated());
        assertEquals(dbGate2.getLastModified().toInstant(), gates.get(1).getLastModified());
        assertEquals(dbGate2.getStatus(), gates.get(1).getStatus());
    }


    @Test
    public void testGetAllGates_Empty() throws Exception {

        Mockito.when(gateRepository.findAll()).thenReturn(new ArrayList<>());

        List<Gate> gates = gateService.getAllGates();

        assertNotNull(gates);
        assertEquals(0, gates.size());
    }


    @Test
    public void testCreateGate_Link() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.save(Mockito.any(DBGate.class))).thenReturn(dbGate);

        CreateGateRequest request = createCreateGateRequest(OperationStatus.READY_FOR_OPERATION, "SYSTEM", "XYZ");
        Link link = gateService.createGate(request);

        assertNotNull(link);
        assertEquals("1", link.getId());
        assertEquals("/api/v1/gates/1", link.getLink());
    }


    @Test
    public void testCreateGate_RequestBody_Saved() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        ArgumentCaptor<DBGate> argumentCaptor = ArgumentCaptor.forClass(DBGate.class);
        Mockito.when(gateRepository.save(argumentCaptor.capture())).thenReturn(dbGate);

        CreateGateRequest request = createCreateGateRequest(OperationStatus.READY_FOR_OPERATION, "SYSTEM", "XYZ");
        Link link = gateService.createGate(request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(request.getStatus(), argumentCaptor.getValue().getStatus());
        assertEquals(request.getCreatedBy(), argumentCaptor.getValue().getCreatedBy());
        assertEquals(request.getCreateReason(), argumentCaptor.getValue().getLastModifiedReason());
    }


    @Test
    public void testCreateGate_NoRequestBody_Saved() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        ArgumentCaptor<DBGate> argumentCaptor = ArgumentCaptor.forClass(DBGate.class);
        Mockito.when(gateRepository.save(argumentCaptor.capture())).thenReturn(dbGate);

        CreateGateRequest request = createCreateGateRequest_Empty();
        Link link = gateService.createGate(request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(OperationStatus.READY_FOR_OPERATION, argumentCaptor.getValue().getStatus());
        assertNull(argumentCaptor.getValue().getCreatedBy());
        assertNull(argumentCaptor.getValue().getLastModifiedReason());
    }



    @Test
    public void testUpdateGate_UpdateStatus() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);

        ArgumentCaptor<DBGate> argumentCaptor = ArgumentCaptor.forClass(DBGate.class);
        Mockito.when(gateRepository.save(argumentCaptor.capture())).thenReturn(dbGate);

        UpdateGateRequest request = createUpdateGateRequest(OperationStatus.CLOSED_FOR_MAINTENANCE, null, null);
        Link link = gateService.updateGate("1", request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(request.getStatus(), argumentCaptor.getValue().getStatus());
        assertNotNull(argumentCaptor.getValue().getLastModifiedBy());
        assertNotNull(argumentCaptor.getValue().getLastModifiedReason());
    }


    @Test
    public void testUpdateGate_UpdateModifiedBy() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);

        ArgumentCaptor<DBGate> argumentCaptor = ArgumentCaptor.forClass(DBGate.class);
        Mockito.when(gateRepository.save(argumentCaptor.capture())).thenReturn(dbGate);

        UpdateGateRequest request = createUpdateGateRequest(null, "User", null);
        Link link = gateService.updateGate("1", request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(request.getModifiedBy(), argumentCaptor.getValue().getLastModifiedBy());
        assertNotNull(argumentCaptor.getValue().getStatus());
        assertNotNull(argumentCaptor.getValue().getLastModifiedReason());
    }


    @Test
    public void testUpdateGate_UpdateModifyReason() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);

        ArgumentCaptor<DBGate> argumentCaptor = ArgumentCaptor.forClass(DBGate.class);
        Mockito.when(gateRepository.save(argumentCaptor.capture())).thenReturn(dbGate);

        UpdateGateRequest request = createUpdateGateRequest(null, null, "Reason");
        Link link = gateService.updateGate("1", request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(request.getModifyReason(), argumentCaptor.getValue().getLastModifiedReason());
        assertNotNull(argumentCaptor.getValue().getStatus());
        assertNotNull(argumentCaptor.getValue().getLastModifiedBy());
    }


    @Test
    public void testUpdateGate_Link() throws Exception {

        DBGate dbGate = createDBGate(1L, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", "XYZ");

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);
        Mockito.when(gateRepository.save(Mockito.any(DBGate.class))).thenReturn(dbGate);

        UpdateGateRequest request = createUpdateGateRequest(null, null, "Reason");
        Link link = gateService.updateGate("1", request);

        assertNotNull(link);
        assertEquals("1", link.getId());
        assertEquals("/api/v1/gates/1", link.getLink());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateGate_ResourceNotFound() throws Exception {

        Mockito.when(gateRepository.findOne(1L)).thenReturn(null);

        UpdateGateRequest request = createUpdateGateRequest(null, null, "Reason");
        gateService.updateGate("1", request);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testDeleteGate_ResourceNotFound() throws Exception {

        Mockito.when(gateRepository.findOne(1L)).thenReturn(null);

        gateService.deleteGate("1");
    }


    private static DBGate createDBGate(Long id, OperationStatus status, String createdBy, String lastModifiedBy, String lastModifiedReason) {
        DBGate dbGate = new DBGate();
        dbGate.setId(id);
        dbGate.setStatus(status);
        dbGate.setCreated(new Date());
        dbGate.setCreatedBy(createdBy);
        dbGate.setLastModified(new Date());
        dbGate.setLastModifiedBy(lastModifiedBy);
        dbGate.setLastModifiedReason(lastModifiedReason);
        return dbGate;
    }


    private static CreateGateRequest createCreateGateRequest(OperationStatus status, String createdBy, String createReason) {
        CreateGateRequest request = new CreateGateRequest();
        request.setStatus(status);
        request.setCreatedBy(createdBy);
        request.setCreateReason(createReason);
        return request;
    }


    private static CreateGateRequest createCreateGateRequest_Empty() {
        return new CreateGateRequest();
    }


    private static UpdateGateRequest createUpdateGateRequest(OperationStatus status, String modifiedBy, String modifyReason) {
        UpdateGateRequest request = new UpdateGateRequest();
        request.setStatus(status);
        request.setModifiedBy(modifiedBy);
        request.setModifyReason(modifyReason);
        return request;
    }


}