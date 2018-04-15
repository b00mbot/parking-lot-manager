package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tickets")
public class ParkingTicketController {


    @RequestMapping(method = RequestMethod.GET, path = "/{ticketID}")
    public ResponseEntity getTicketByID(@PathVariable String ticketID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity getAllTickets() {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity issueTicket() {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.PATCH, path = "/{ticketID}")
    public ResponseEntity updateTicketByID(@PathVariable String ticketID, @RequestBody UpdateTicketRequest updateTicketRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
