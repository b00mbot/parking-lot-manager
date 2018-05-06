package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.Error;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.services.ParkingTicketService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Parking Tickets", description = "Operations involving parking tickets")
@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingTicketController {

    private ParkingTicketService service;

    @Autowired
    public ParkingTicketController(ParkingTicketService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get parking ticket by ID", nickname = "getTicketByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Ticket.class),
            @ApiResponse(code = 404, message = "Resource not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{ticketID}")
    public ResponseEntity<Ticket> getTicketByID(
            @ApiParam(required = true, value = "ID of parking ticket to return")
            @PathVariable String ticketID) {
        Ticket parkingTicket = service.getParkingTicket(ticketID);
        return new ResponseEntity<>(parkingTicket, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Get all parking tickets", nickname = "getAllTickets", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Ticket.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> parkingTickets = service.getAllParkingTickets();
        return new ResponseEntity<>(parkingTickets, HttpStatus.OK);
    }


    @ApiOperation(value = "Issue parking ticket", nickname = "issueTicket", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully issued", response = Link.class),
            @ApiResponse(code = 204, message = "Parking lot capacity reached", response = void.class),
            @ApiResponse(code = 400, message = "Bad request", response = Link.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> issueTicket(
            @ApiParam(value = "Request body")
            @Valid @RequestBody IssueTicketRequest issueTicketRequest) {
        Link link = service.issueParkingTicket(issueTicketRequest);
        return new ResponseEntity<>(link, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update parking ticket by ID", nickname = "updateTicketByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = Link.class),
            @ApiResponse(code = 400, message = "Bad request", response = Link.class),
            @ApiResponse(code = 404, message = "Resource not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.PATCH, path = "/{ticketID}")
    public ResponseEntity<Link> updateTicketByID(
            @ApiParam(required = true, value = "ID of parking ticket to update")
            @PathVariable String ticketID,
            @ApiParam(required = true, value = "Request body")
            @Valid @RequestBody UpdateTicketRequest updateTicketRequest) {
        Link link = service.updateParkingTicket(ticketID, updateTicketRequest);
        return new ResponseEntity<>(link, HttpStatus.OK);
    }


}
