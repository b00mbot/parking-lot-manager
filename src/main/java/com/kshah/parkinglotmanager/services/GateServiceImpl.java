package com.kshah.parkinglotmanager.services;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.repositories.GateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GateServiceImpl implements GateService {

    private GateRepository gateRepository;

    @Autowired
    public GateServiceImpl(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
    }

    @Override
    public Gate getGate(String id) {

        DBGate dbGate = gateRepository.findOne(Long.parseLong(id));

        if(dbGate == null) {
            return null;
        }

        Gate gate = new Gate();
        gate.setId(id);
        gate.setCreated(dbGate.getCreated().toInstant());
        gate.setLastModified(dbGate.getLastModified().toInstant());
        gate.setStatus(dbGate.getStatus());
        return gate;
    }

    @Override
    public List<Gate> getAllGates() {

        List<Gate> gates = new ArrayList<>();

        for(DBGate dbGate : gateRepository.findAll()) {
            Gate gate = new Gate();
            gate.setId(dbGate.getId().toString());
            gate.setStatus(dbGate.getStatus());
            gate.setCreated(dbGate.getCreated().toInstant());
            gate.setLastModified(dbGate.getLastModified().toInstant());
            gates.add(gate);
        }

        return gates;
    }

    @Override
    public Link createGate(CreateGateRequest request) {

        DBGate gate = new DBGate();

        // Set status - default to READY FOR OPERATION if not set in request
        gate.setStatus(request.getStatus() != null ? request.getStatus() : OperationStatus.READY_FOR_OPERATION);

        // Only set create user if it was set in the request
        String user = request.getCreatedBy();
        if(user != null) {
            gate.setCreatedBy(user);
            gate.setLastModifiedBy(user);
        }

        // Set create reason
        gate.setLastModifiedReason(request.getCreateReason());

        // Create gate
        DBGate createdGate = gateRepository.save(gate);

        // Prepare response
        Link link = new Link();
        String id = createdGate.getId().toString();
        link.setId(id);
        link.setLink("/api/v1/gates/" + id);
        return link;
    }

    @Override
    public Link updateGate(String id, UpdateGateRequest request) {

        Link linkToReturn = null;

        DBGate gateFromDB = gateRepository.findOne(Long.parseLong(id));

        if(gateFromDB != null) {

            if(request.getStatus() != null) {
                gateFromDB.setStatus(request.getStatus());
            }

            if(request.getModifiedBy() != null) {
                gateFromDB.setLastModifiedBy(request.getModifiedBy());
            }

            if(request.getModifyReason() != null) {
                gateFromDB.setLastModifiedReason(request.getModifyReason());
            }

            gateRepository.save(gateFromDB);

            linkToReturn = new Link();
            linkToReturn.setId(id);
            linkToReturn.setLink("/api/v1/gates/" + id);
        }

        return linkToReturn;
    }


    @Override
    public void deleteGate(String id) {
        gateRepository.delete(Long.parseLong(id));
    }

}
