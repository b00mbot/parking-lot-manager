package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.Capacity;
import com.kshah.parkinglotmanager.model.api.Error;
import com.kshah.parkinglotmanager.services.CapacityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Parking Capacity", description = "Operations involving parking lot capacity")
@RestController
@RequestMapping("/api/v1/capacity")
public class ParkingCapacityController {

    private CapacityService service;

    @Autowired
    public ParkingCapacityController(CapacityService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get parking lot capacity", nickname = "getParkingLotCapacity", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Capacity.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<Capacity> getParkingLotCapacity() {
        Capacity parkingCapacity = service.getCapacity();
        return new ResponseEntity<>(parkingCapacity, HttpStatus.OK);
    }

}
