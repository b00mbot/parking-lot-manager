package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.*;
import com.kshah.parkinglotmanager.model.common.TicketStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.model.database.DBTicket;
import com.kshah.parkinglotmanager.repositories.GateRepository;
import com.kshah.parkinglotmanager.repositories.ParkingTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParkingTicketServiceImpl implements ParkingTicketService {

    private ParkingTicketRepository ticketRepository;
    private GateRepository gateRepository;

    @Autowired
    public ParkingTicketServiceImpl(ParkingTicketRepository ticketRepository, GateRepository gateRepository) {
        this.ticketRepository = ticketRepository;
        this.gateRepository = gateRepository;
    }


    @Override
    public Ticket getParkingTicket(String id) {

        DBTicket dbTicket = ticketRepository.findOne(Long.parseLong(id));

        if(dbTicket == null) {
            throw new ResourceNotFoundException("Parking ticket with id '" + id + "' does not exist");
        }

        Ticket ticket = new Ticket();
        ticket.setId(id);
        ticket.setIssued(dbTicket.getCreated().toInstant());
        ticket.setLastModified(dbTicket.getLastModified().toInstant());
        ticket.setStatus(dbTicket.getStatus());

        Link link = new Link();
        String gateID = dbTicket.getGate().getId().toString();
        link.setId(gateID);
        link.setLink("/api/v1/gates/" + gateID);
        ticket.setGateIssuedAt(link);

        return ticket;
    }

    @Override
    public List<Ticket> getAllParkingTickets() {
        List<Ticket> tickets = new ArrayList<>();

        for(DBTicket dbTicket : ticketRepository.findAll()) {
            Ticket ticket = new Ticket();
            ticket.setId(dbTicket.getId().toString());
            ticket.setStatus(dbTicket.getStatus());
            ticket.setIssued(dbTicket.getCreated().toInstant());
            ticket.setLastModified(dbTicket.getLastModified().toInstant());

            Link link = new Link();
            String gateID = dbTicket.getGate().getId().toString();
            link.setId(gateID);
            link.setLink("/api/v1/gates/" + gateID);
            ticket.setGateIssuedAt(link);

            tickets.add(ticket);
        }

        return tickets;
    }

    @Override
    public Link issueParkingTicket(IssueTicketRequest request) {

        // Check if gate ID exists
        DBGate dbGate = gateRepository.findOne(Long.valueOf(request.getGateId()));
        if(dbGate == null) {
            throw new BadDataException("Gate with id '" + request.getGateId() + "' does not exist");
        }

        DBTicket ticket = new DBTicket();

        // TODO - Need check to see if parking lot capacity has been reached

        // Set status - default to ISSUED if not set in request
        ticket.setStatus(request.getStatus() != null ? request.getStatus() : TicketStatus.ISSUED);

        // Only set create user if it was set in the request
        String user = request.getCreatedBy();
        if(user != null) {
            ticket.setCreatedBy(user);
            ticket.setLastModifiedBy(user);
        }

        // Set create reason
        ticket.setLastModifiedReason(request.getCreateReason());

        // Set gate
        ticket.setGate(dbGate);

        // Create gate
        DBTicket createdTicket = ticketRepository.save(ticket);

        // Prepare response
        Link link = new Link();
        String id = createdTicket.getId().toString();
        link.setId(id);
        link.setLink("/api/v1/tickets/" + id);
        return link;
    }

    @Override
    public Link updateParkingTicket(String id, UpdateTicketRequest request) {
        DBTicket ticketFromDB = ticketRepository.findOne(Long.parseLong(id));

        if(ticketFromDB == null) {
            throw new ResourceNotFoundException("Parking ticket with id '" + id + "' does not exist");
        }

        if(request.getStatus() != null) {
            ticketFromDB.setStatus(request.getStatus());
        }

        if(request.getModifiedBy() != null) {
            ticketFromDB.setLastModifiedBy(request.getModifiedBy());
        }

        if(request.getModifyReason() != null) {
            ticketFromDB.setLastModifiedReason(request.getModifyReason());
        }

        ticketRepository.save(ticketFromDB);

        Link linkToReturn = new Link();
        linkToReturn.setId(id);
        linkToReturn.setLink("/api/v1/tickets/" + id);

        return linkToReturn;
    }
}
