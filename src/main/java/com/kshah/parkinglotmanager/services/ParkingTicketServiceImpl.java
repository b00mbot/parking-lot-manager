package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.exceptions.BadDataException;
import com.kshah.parkinglotmanager.exceptions.ParkingLotCapacityReachedException;
import com.kshah.parkinglotmanager.exceptions.ResourceNotFoundException;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.model.common.TicketStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.model.database.DBTicket;
import com.kshah.parkinglotmanager.repositories.GateRepository;
import com.kshah.parkinglotmanager.repositories.ParkingTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
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
        // Set status to ISSUED
        ticket.setStatus(TicketStatus.ISSUED);

        // Set gate
        ticket.setGate(dbGate);

        // Create ticket
        DBTicket createdTicket = null;
        try {
            createdTicket = ticketRepository.save(ticket);
        } catch (JpaSystemException e) {

            // If we get a SQL exception with SQL state of 45000, then we know that max capacity has been reached as we have defined a database trigger
            if(e.getRootCause() instanceof SQLException) {
                SQLException se = (SQLException) e.getRootCause();
                if(se.getSQLState() != null && se.getSQLState().equals("45000")) {
                    throw new ParkingLotCapacityReachedException("Parking lot max capacity has been reached", e);
                }
            }

            throw e;
        }

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

        // Set status to COMPLETED
        ticketFromDB.setStatus(TicketStatus.COMPLETED);

        ticketRepository.save(ticketFromDB);

        Link linkToReturn = new Link();
        linkToReturn.setId(id);
        linkToReturn.setLink("/api/v1/tickets/" + id);

        return linkToReturn;
    }
}
