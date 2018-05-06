package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.fixtures.DBEntityTestFixtures;
import com.kshah.parkinglotmanager.fixtures.RestAPITestFixtures;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.model.common.TicketStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.model.database.DBTicket;
import com.kshah.parkinglotmanager.repositories.GateRepository;
import com.kshah.parkinglotmanager.repositories.ParkingTicketRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.orm.jpa.JpaSystemException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ParkingTicketServiceImplTest {

    @Mock
    private GateRepository gateRepository;

    @Mock
    private ParkingTicketRepository ticketRepository;

    @Mock
    private JpaSystemException jpaSystemException;

    private ParkingTicketServiceImpl ticketService;

    @Before
    public void setUp() throws Exception {
        ticketService = new ParkingTicketServiceImpl(ticketRepository, gateRepository);
    }

    @Test
    public void testGetParkingTicket() throws Exception {

        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 2L);

        Mockito.when(ticketRepository.findOne(1L)).thenReturn(dbTicket);

        Ticket actualTicket = ticketService.getParkingTicket("1");

        assertNotNull(actualTicket);
        assertEquals("1", actualTicket.getId());
        assertEquals(dbTicket.getCreated().toInstant(), actualTicket.getIssued());
        assertEquals(dbTicket.getLastModified().toInstant(), actualTicket.getLastModified());
        assertEquals(dbTicket.getStatus(), actualTicket.getStatus());
        assertNotNull(actualTicket.getGateIssuedAt());
        assertEquals("2", actualTicket.getGateIssuedAt().getId());
        assertEquals("/api/v1/gates/2", actualTicket.getGateIssuedAt().getLink());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testGetParkingTicket_ResourceNotFound() throws Exception {

        Mockito.when(ticketRepository.findOne(1L)).thenReturn(null);

        ticketService.getParkingTicket("2");
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testGetParkingTicket_ResourceNotFound_IdNotLong() throws Exception {
        ticketService.getParkingTicket("abc");
    }


    @Test
    public void testGetAllParkingTickets() throws Exception {

        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, TicketStatus.ISSUED, "SYSTEM", "SYSTEM", "XYZ", 2L);
        DBTicket dbTicket2 = DBEntityTestFixtures.dbTicket(2L, TicketStatus.COMPLETED, "SYSTEM", "SYSTEM", "ABC", 3L);

        Mockito.when(ticketRepository.findAll()).thenReturn(Arrays.asList(dbTicket, dbTicket2));

        List<Ticket> tickets = ticketService.getAllParkingTickets();

        assertNotNull(tickets);
        assertEquals(2, tickets.size());

        assertEquals("1", tickets.get(0).getId());
        assertEquals(dbTicket.getCreated().toInstant(), tickets.get(0).getIssued());
        assertEquals(dbTicket.getLastModified().toInstant(), tickets.get(0).getLastModified());
        assertEquals(dbTicket.getStatus(), tickets.get(0).getStatus());
        assertNotNull(tickets.get(0).getGateIssuedAt());
        assertEquals("2", tickets.get(0).getGateIssuedAt().getId());
        assertEquals("/api/v1/gates/2", tickets.get(0).getGateIssuedAt().getLink());

        assertEquals("2", tickets.get(1).getId());
        assertEquals(dbTicket2.getCreated().toInstant(), tickets.get(1).getIssued());
        assertEquals(dbTicket2.getLastModified().toInstant(), tickets.get(1).getLastModified());
        assertEquals(dbTicket2.getStatus(), tickets.get(1).getStatus());
        assertNotNull(tickets.get(1).getGateIssuedAt());
        assertEquals("3", tickets.get(1).getGateIssuedAt().getId());
        assertEquals("/api/v1/gates/3", tickets.get(1).getGateIssuedAt().getLink());
    }


    @Test
    public void testGetAllParkingTickets_Empty() throws Exception {

        Mockito.when(ticketRepository.findAll()).thenReturn(new ArrayList<>());

        List<Ticket> tickets = ticketService.getAllParkingTickets();

        assertNotNull(tickets);
        assertEquals(0, tickets.size());
    }


    @Test
    public void testIssueParkingTicket_Link() throws Exception {

        DBGate dbGate = DBEntityTestFixtures.dbGate(1L);
        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 1L);

        Mockito.when(gateRepository.findOne(1L)).thenReturn(dbGate);
        Mockito.when(ticketRepository.save(Mockito.any(DBTicket.class))).thenReturn(dbTicket);

        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("1");
        Link link = ticketService.issueParkingTicket(request);

        assertNotNull(link);
        assertEquals("1", link.getId());
        assertEquals("/api/v1/tickets/1", link.getLink());
    }


    @Test
    public void testIssueParkingTicket_RequestBody_Saved() throws Exception {

        DBGate dbGate = DBEntityTestFixtures.dbGate(2L);
        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 2L);

        Mockito.when(gateRepository.findOne(2L)).thenReturn(dbGate);
        ArgumentCaptor<DBTicket> argumentCaptor = ArgumentCaptor.forClass(DBTicket.class);
        Mockito.when(ticketRepository.save(argumentCaptor.capture())).thenReturn(dbTicket);

        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("2");
        Link link = ticketService.issueParkingTicket(request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(dbGate, argumentCaptor.getValue().getGate());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testIssueParkingTicket_GateResourceNotFound() throws Exception {

        Mockito.when(gateRepository.findOne(2L)).thenReturn(null);

        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("2");
        Link link = ticketService.issueParkingTicket(request);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testIssueParkingTicket_GateResourceNotFound_IdNotLong() throws Exception {
        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("abc");
        Link link = ticketService.issueParkingTicket(request);
    }


    @Test(expected = ParkingLotCapacityReachedException.class)
    public void testIssueParkingTicket_ParkingLotCapacityReached() throws Exception {

        DBGate dbGate = DBEntityTestFixtures.dbGate(2L);
        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 2L);

        SQLException sqlException = new SQLException(null, "45000", null);
        Mockito.when(jpaSystemException.getRootCause()).thenReturn(sqlException);

        Mockito.when(gateRepository.findOne(2L)).thenReturn(dbGate);
        Mockito.when(ticketRepository.save(Mockito.any(DBTicket.class))).thenThrow(jpaSystemException);

        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("2");
        ticketService.issueParkingTicket(request);
    }


    @Test
    public void testUpdateParkingTicket_Complete() throws Exception {

        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 2L);

        Mockito.when(ticketRepository.findOne(1L)).thenReturn(dbTicket);

        ArgumentCaptor<DBTicket> argumentCaptor = ArgumentCaptor.forClass(DBTicket.class);
        Mockito.when(ticketRepository.save(argumentCaptor.capture())).thenReturn(dbTicket);

        UpdateTicketRequest request = RestAPITestFixtures.updateTicketRequest();
        Link link = ticketService.updateParkingTicket("1", request);

        assertNotNull(argumentCaptor.getValue());
        assertEquals(TicketStatus.COMPLETED, argumentCaptor.getValue().getStatus());
        assertNotNull(argumentCaptor.getValue().getLastModifiedBy());
        assertNotNull(argumentCaptor.getValue().getLastModifiedReason());
        assertNotNull(argumentCaptor.getValue().getGate());
    }


    @Test
    public void testUpdateParkingTicket_Link() throws Exception {

        DBTicket dbTicket = DBEntityTestFixtures.dbTicket(1L, 2L);

        Mockito.when(ticketRepository.findOne(1L)).thenReturn(dbTicket);
        Mockito.when(ticketRepository.save(Mockito.any(DBTicket.class))).thenReturn(dbTicket);

        UpdateTicketRequest request = RestAPITestFixtures.updateTicketRequest();
        Link link = ticketService.updateParkingTicket("1", request);

        assertNotNull(link);
        assertEquals("1", link.getId());
        assertEquals("/api/v1/tickets/1", link.getLink());
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateParkingTicket_ResourceNotFound() throws Exception {

        Mockito.when(ticketRepository.findOne(1L)).thenReturn(null);

        UpdateTicketRequest request = RestAPITestFixtures.updateTicketRequest();
        ticketService.updateParkingTicket("1", request);
    }


    @Test(expected = ResourceNotFoundException.class)
    public void testUpdateParkingTicket_ResourceNotFound_IdNotLong() throws Exception {
        UpdateTicketRequest request = RestAPITestFixtures.updateTicketRequest();
        ticketService.updateParkingTicket("abc", request);
    }

}