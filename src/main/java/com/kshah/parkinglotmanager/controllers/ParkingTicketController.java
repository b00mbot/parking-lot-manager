package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "Parking Tickets", description = "Operations involving parking tickets")
@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingTicketController {


    @ApiOperation(value = "Get parking ticket by ID", nickname = "getTicketByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Ticket.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{ticketID}")
    public ResponseEntity<Ticket> getTicketByID(
            @ApiParam(required = true, value = "ID of parking ticket to return")
            @PathVariable String ticketID) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Get all parking tickets", nickname = "getAllTickets", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Ticket.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Ticket>> getAllTickets() {
        // TODO: Change once implementation is done
        return new ResponseEntity<List<Ticket>>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Issue parking ticket", nickname = "issueTicket", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully issued", response = Ticket.class),
            @ApiResponse(code = 204, message = "Parking lot capacity reached", response = void.class)
    })
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Ticket> issueTicket() {
        // TODO: Change once implementation is done
        return new ResponseEntity<Ticket>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Update parking ticket by ID", nickname = "updateTicketByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = Link.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.PATCH, path = "/{ticketID}")
    public ResponseEntity<Link> updateTicketByID(
            @ApiParam(required = true, value = "ID of parking ticket to update")
            @PathVariable String ticketID,
            @ApiParam(required = true, value = "Request body")
            @RequestBody UpdateTicketRequest updateTicketRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Link>(HttpStatus.NOT_IMPLEMENTED);
    }


}
