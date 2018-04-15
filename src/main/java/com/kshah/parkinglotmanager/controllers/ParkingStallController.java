package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.CreateStallRequest;
import com.kshah.parkinglotmanager.model.api.UpdateStallRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stalls")
public class ParkingStallController {

    @RequestMapping(method = RequestMethod.GET, path = "/{stallID}")
    public ResponseEntity getParkingStallByID(@PathVariable String stallID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity getAllParkingStalls() {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity createParkingStall(@RequestBody CreateStallRequest createStallRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.PATCH, path = "/{stallID}")
    public ResponseEntity updateParkingStallByID(@PathVariable String stallID, @RequestBody UpdateStallRequest updateStallRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.DELETE, path = "/{stallID}")
    public ResponseEntity deleteParkingStallByID(@PathVariable String stallID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
