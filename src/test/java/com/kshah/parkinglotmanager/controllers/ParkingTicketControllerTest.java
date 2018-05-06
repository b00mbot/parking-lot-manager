package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.fixtures.RestAPITestFixtures;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.model.common.TicketStatus;
import com.kshah.parkinglotmanager.services.ParkingTicketService;
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
public class ParkingTicketControllerTest {

    @Mock
    private ParkingTicketService ticketService;

    private ParkingTicketController parkingTicketController;


    @Before
    public void setUp() throws Exception {
        parkingTicketController = new ParkingTicketController(ticketService);
    }

    @Test
    public void getTicketByID() {
        Ticket ticket = RestAPITestFixtures.createTicket("1", "2", TicketStatus.ISSUED);
        Mockito.when(ticketService.getParkingTicket("1")).thenReturn(ticket);

        ResponseEntity responseEntity = parkingTicketController.getTicketByID("1");

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Ticket.class, responseEntity.getBody().getClass());
        assertEquals(ticket.getId(), ((Ticket) responseEntity.getBody()).getId());
        assertEquals(ticket.getIssued(), ((Ticket) responseEntity.getBody()).getIssued());
        assertEquals(ticket.getLastModified(), ((Ticket) responseEntity.getBody()).getLastModified());
        assertEquals(ticket.getStatus(), ((Ticket) responseEntity.getBody()).getStatus());
        assertEquals(ticket.getGateIssuedAt().getId(), ((Ticket) responseEntity.getBody()).getGateIssuedAt().getId());
        assertEquals(ticket.getGateIssuedAt().getLink(), ((Ticket) responseEntity.getBody()).getGateIssuedAt().getLink());
    }

    @Test
    public void getAllTickets() {
        Ticket ticket1 = RestAPITestFixtures.createTicket("1", "2", TicketStatus.ISSUED);
        Ticket ticket2 = RestAPITestFixtures.createTicket("2", "1", TicketStatus.COMPLETED);
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        Mockito.when(ticketService.getAllParkingTickets()).thenReturn(tickets);

        ResponseEntity responseEntity = parkingTicketController.getAllTickets();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, ((List<Ticket>) responseEntity.getBody()).size());

        List<Ticket> actualTickets = ((List<Ticket>) responseEntity.getBody());
        assertEquals(ticket1.getId(), actualTickets.get(0).getId());
        assertEquals(ticket1.getIssued(), actualTickets.get(0).getIssued());
        assertEquals(ticket1.getLastModified(), actualTickets.get(0).getLastModified());
        assertEquals(ticket1.getStatus(), actualTickets.get(0).getStatus());
        assertEquals(ticket1.getGateIssuedAt().getId(), actualTickets.get(0).getGateIssuedAt().getId());
        assertEquals(ticket1.getGateIssuedAt().getLink(), actualTickets.get(0).getGateIssuedAt().getLink());
        assertEquals(ticket2.getId(), actualTickets.get(1).getId());
        assertEquals(ticket2.getIssued(), actualTickets.get(1).getIssued());
        assertEquals(ticket2.getLastModified(), actualTickets.get(1).getLastModified());
        assertEquals(ticket2.getStatus(), actualTickets.get(1).getStatus());
        assertEquals(ticket2.getGateIssuedAt().getId(), actualTickets.get(1).getGateIssuedAt().getId());
        assertEquals(ticket2.getGateIssuedAt().getLink(), actualTickets.get(1).getGateIssuedAt().getLink());
    }


    @Test
    public void getAllTickets_Empty() {
        Mockito.when(ticketService.getAllParkingTickets()).thenReturn(new ArrayList<>());

        ResponseEntity responseEntity = parkingTicketController.getAllTickets();

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(0, ((List<Ticket>) responseEntity.getBody()).size());
    }


    @Test
    public void issueTicket() {
        Link link = RestAPITestFixtures.createTicketLink("1");
        IssueTicketRequest request = RestAPITestFixtures.issueTicketRequest("1");
        Mockito.when(ticketService.issueParkingTicket(request)).thenReturn(link);

        ResponseEntity responseEntity = parkingTicketController.issueTicket(request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Link.class, responseEntity.getBody().getClass());
        assertEquals(link.getId(), ((Link) responseEntity.getBody()).getId());
        assertEquals(link.getLink(), ((Link) responseEntity.getBody()).getLink());
    }

    @Test
    public void updateTicketByID() {
        Link link = RestAPITestFixtures.createTicketLink("1");
        UpdateTicketRequest request = RestAPITestFixtures.updateTicketRequest();
        Mockito.when(ticketService.updateParkingTicket("1", request)).thenReturn(link);

        ResponseEntity responseEntity = parkingTicketController.updateTicketByID("1", request);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(Link.class, responseEntity.getBody().getClass());
        assertEquals(link.getId(), ((Link) responseEntity.getBody()).getId());
        assertEquals(link.getLink(), ((Link) responseEntity.getBody()).getLink());
    }
}