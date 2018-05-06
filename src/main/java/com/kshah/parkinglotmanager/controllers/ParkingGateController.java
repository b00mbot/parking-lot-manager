package com.kshah.parkinglotmanager.controllers;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Error;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.services.GateService;
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

@Api(tags = "Gates", description = "Operations involving parking gates")
@RestController
@RequestMapping("/api/v1/gates")
public class ParkingGateController {

    private GateService gateService;

    @Autowired
    public ParkingGateController(GateService gateService) {
        this.gateService = gateService;
    }

    @ApiOperation(value = "Get gate by ID", nickname = "getGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Gate.class),
            @ApiResponse(code = 404, message = "Resource not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.GET, path = "/{gateID}")
    public ResponseEntity<Gate> getGateByID(
            @ApiParam(required = true, value = "ID of gate to return")
            @PathVariable String gateID) {
        Gate gate = gateService.getGate(gateID);
        return new ResponseEntity<>(gate, HttpStatus.OK);
    }


    @ApiOperation(value = "Get all gates", nickname = "getAllGates", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Gate.class, responseContainer = "List"),
            @ApiResponse(code = 500, message = "Internal sserver error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.GET, path = "")
    public ResponseEntity<List<Gate>> getAllGates() {
        List<Gate> gates = gateService.getAllGates();
        return new ResponseEntity<>(gates, HttpStatus.OK);
    }


    @ApiOperation(value = "Create gate", nickname = "createGate", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created", response = Link.class),
            @ApiResponse(code = 400, message = "Bad request", response = Link.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.POST, path = "")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Link> createGate(
            @ApiParam(value = "Request body")
            @Valid @RequestBody CreateGateRequest createGateRequest) {
        Link link = gateService.createGate(createGateRequest);
        return new ResponseEntity<>(link, HttpStatus.CREATED);
    }


    @ApiOperation(value = "Update gate by ID", nickname = "updateGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated", response = Link.class),
            @ApiResponse(code = 400, message = "Bad request", response = Link.class),
            @ApiResponse(code = 404, message = "Resource not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.PATCH, path = "/{gateID}")
    public ResponseEntity<Link> updateGateByID(
            @ApiParam(required = true, value = "ID of gate to update")
            @PathVariable String gateID,
            @ApiParam(required = true, value = "Request body")
            @Valid @RequestBody UpdateGateRequest updateGateRequest) {
        Link link = gateService.updateGate(gateID, updateGateRequest);
        return new ResponseEntity<>(link, HttpStatus.OK);
    }


    @ApiOperation(value = "Delete gate by ID", nickname = "deleteGateByID", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted", response = void.class),
            @ApiResponse(code = 404, message = "Resource not found", response = Error.class),
            @ApiResponse(code = 500, message = "Internal server error", response = Error.class)
    })
    @RequestMapping(method = RequestMethod.DELETE, path = "/{gateID}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity deleteGateByID(
            @ApiParam(required = true, value = "ID of gate to delete")
            @PathVariable String gateID) {
        gateService.deleteGate(gateID);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
