package com.kshah.parkinglotmanager.fixtures;

import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.model.common.TicketStatus;
import com.kshah.parkinglotmanager.model.database.DBGate;
import com.kshah.parkinglotmanager.model.database.DBTicket;

import java.util.Date;

public class DBEntityTestFixtures {


    public static DBGate dbGate(Long id) {
        return dbGate(id, OperationStatus.OPERATIONAL, "SYSTEM", "SYSTEM", "Reason");
    }


    public static DBGate dbGate(Long id, OperationStatus status, String createdBy, String lastModifiedBy, String lastModifiedReason) {
        DBGate dbGate = new DBGate();
        dbGate.setId(id);
        dbGate.setStatus(status);
        dbGate.setCreated(new Date());
        dbGate.setCreatedBy(createdBy);
        dbGate.setLastModified(new Date());
        dbGate.setLastModifiedBy(lastModifiedBy);
        dbGate.setLastModifiedReason(lastModifiedReason);
        return dbGate;
    }


    public static DBTicket dbTicket(Long id, Long gateIssuedAtId) {
        return dbTicket(id, TicketStatus.ISSUED, "SYSTEM", "SYSTEM", "Reason", gateIssuedAtId);
    }


    public static DBTicket dbTicket(Long id, TicketStatus status, String createdBy, String lastModifiedBy, String lastModifiedReason, Long gateIssuedId) {
        DBTicket dbTicket = new DBTicket();
        dbTicket.setId(id);
        dbTicket.setStatus(status);
        dbTicket.setCreated(new Date());
        dbTicket.setCreatedBy(createdBy);
        dbTicket.setLastModified(new Date());
        dbTicket.setLastModifiedBy(lastModifiedBy);
        dbTicket.setLastModifiedReason(lastModifiedReason);
        dbTicket.setGate(dbGate(gateIssuedId, OperationStatus.READY_FOR_OPERATION, "SYSTEM", "SYSTEM", null));
        return dbTicket;
    }


}
