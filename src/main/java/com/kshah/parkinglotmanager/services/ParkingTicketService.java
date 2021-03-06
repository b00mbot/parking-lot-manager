package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;

import java.util.List;

public interface ParkingTicketService {


    Ticket getParkingTicket(String id);

    List<Ticket> getAllParkingTickets();

    Link issueParkingTicket(IssueTicketRequest request);

    Link updateParkingTicket(String id, UpdateTicketRequest request);

}
