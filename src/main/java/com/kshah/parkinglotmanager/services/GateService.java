package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;

import java.util.List;

public interface GateService {


    Gate getGate(String id);

    List<Gate> getAllGates();

    Link createGate(CreateGateRequest request);

    Link updateGate(UpdateGateRequest request);

}
