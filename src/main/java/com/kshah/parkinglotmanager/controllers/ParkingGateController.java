package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/gates")
public class ParkingGateController {


    @RequestMapping(method = RequestMethod.GET, path = "/{gateID}")
    public ResponseEntity getGateByID(@PathVariable String gateID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity getAllGates() {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.POST, path = "")
    public ResponseEntity createGate(@RequestBody CreateGateRequest createGateRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.PATCH, path = "/{gateID}")
    public ResponseEntity updateGateByID(@PathVariable String gateID, @RequestBody UpdateGateRequest updateGateRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


    @RequestMapping(method = RequestMethod.DELETE, path = "/{gateID}")
    public ResponseEntity deleteGateByID(@PathVariable String gateID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
