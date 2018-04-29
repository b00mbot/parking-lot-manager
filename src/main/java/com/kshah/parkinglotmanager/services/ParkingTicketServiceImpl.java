package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParkingTicketServiceImpl implements ParkingTicketService {
    @Override
    public Ticket getParkingTicket(String id) {
        return null;
    }

    @Override
    public List<Ticket> getAllParkingTickets() {
        return null;
    }

    @Override
    public Link issueParkingTicket(IssueTicketRequest request) {
        return null;
    }

    @Override
    public Link updateParkingTicket(String id, UpdateTicketRequest request) {
        return null;
    }
}
