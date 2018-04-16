package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
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

@Api(tags = "Gates", description = "Operations involving parking gates")
@RestController
@RequestMapping("/api/v1/gates")
public class ParkingGateController {


    @ApiOperation(value = "Get gate by ID", nickname = "getGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Gate.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{gateID}")
    public ResponseEntity<Gate> getGateByID(
            @ApiParam(required = true, value = "ID of gate to return")
            @PathVariable String gateID) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Gate>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Get all gates", nickname = "getAllGates", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Gate.class, responseContainer = "List")
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Gate>> getAllGates() {
        // TODO: Change once implementation is done
        return new ResponseEntity<List<Gate>>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Create gate", nickname = "createGate", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = Link.class)
    })
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> createGate(
            @ApiParam(value = "Request body")
            @RequestBody CreateGateRequest createGateRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Link>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Update gate by ID", nickname = "updateGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = Link.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.PATCH, path = "/{gateID}")
    public ResponseEntity<Link> updateGateByID(
            @ApiParam(required = true, value = "ID of gate to update")
            @PathVariable String gateID,
            @ApiParam(required = true, value = "Request body")
            @RequestBody UpdateGateRequest updateGateRequest) {
        // TODO: Change once implementation is done
        return new ResponseEntity<Link>(HttpStatus.NOT_IMPLEMENTED);
    }


    @ApiOperation(value = "Delete gate by ID", nickname = "deleteGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted", response = void.class),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/{gateID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGateByID(
            @ApiParam(required = true, value = "ID of gate to delete")
            @PathVariable String gateID) {
        // TODO: Change once implementation is done
        return new ResponseEntity(HttpStatus.NOT_IMPLEMENTED);
    }


}
