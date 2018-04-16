package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.CreateStallRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Stall;
import com.kshah.parkinglotmanager.model.api.UpdateStallRequest;
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

@Api(tags = "Parking Stalls", description = "Operations involving parking stalls")
@RestController
@RequestMapping("/api/v1/stalls")
public class ParkingStallController {

    @ApiOperation(value = "Get parking stall by ID", nickname = "getParkingStallByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Stall.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{stallID}")
    public ResponseEntity<Stall> getParkingStallByID(
            @ApiParam(required = true, value = "ID of parking stall to return")
            @PathVariable String stallID) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Stall>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Get all parking stalls", nickname = "getAllParkingStalls", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Stall.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Stall>> getAllParkingStalls() {
        // TODO: Change once implementation is done
        return new ResponseEntity<List<Stall>>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Create parking stall", nickname = "createParkingStall", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = Link.class)
    })
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> createParkingStall(
            @ApiParam(value = "Request body")
            @RequestBody CreateStallRequest createStallRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Link>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Update parking stall by ID", nickname = "updateParkingStallByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = Link.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.PATCH, path = "/{stallID}")
    public ResponseEntity<Link> updateParkingStallByID(
            @ApiParam(required = true, value = "ID of parking stall to update")
            @PathVariable String stallID,
            @ApiParam(required = true, value = "Request body")
            @RequestBody UpdateStallRequest updateStallRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Link>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Delete parking stall by ID", nickname = "deleteParkingStallByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted", response = void.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/{stallID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteParkingStallByID(
            @ApiParam(required = true, value = "ID of parking stall to delete")
            @PathVariable String stallID) {
        // TODO: Change once implementation is done
    }


}
