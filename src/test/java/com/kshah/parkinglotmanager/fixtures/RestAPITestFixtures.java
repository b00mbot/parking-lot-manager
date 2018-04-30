package com.kshah.parkinglotmanager.fixtures;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.model.common.TicketStatus;

public class RestAPITestFixtures {


    public static CreateGateRequest createGateRequest(OperationStatus status, String createdBy, String createReason) {
        CreateGateRequest request = new CreateGateRequest();
        request.setStatus(status);
        request.setCreatedBy(createdBy);
        request.setCreateReason(createReason);
        return request;
    }

    public static UpdateGateRequest updateGateRequest(OperationStatus status, String modifiedBy, String modifyReason) {
        UpdateGateRequest request = new UpdateGateRequest();
        request.setStatus(status);
        request.setModifiedBy(modifiedBy);
        request.setModifyReason(modifyReason);
        return request;
    }


    public static IssueTicketRequest issueTicketRequest(String gateId) {
        return issueTicketRequest("SYSTEM", "Reason", TicketStatus.ISSUED, gateId);
    }


    public static IssueTicketRequest issueTicketRequest(String createdBy, String createReason, TicketStatus status, String gateId) {
        IssueTicketRequest request = new IssueTicketRequest();
        request.setCreatedBy(createdBy);
        request.setCreateReason(createReason);
        request.setStatus(status);
        request.setGateId(gateId);
        return request;
    }


    public static UpdateTicketRequest updateTicketRequest() {
        return updateTicketRequest("SYSTEM", "Reason", TicketStatus.ISSUED);
    }


    public static UpdateTicketRequest updateTicketRequest(String modifiedBy, String modifyReason, TicketStatus status) {
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setModifiedBy(modifiedBy);
        request.setModifyReason(modifyReason);
        request.setStatus(status);
        return request;
    }



}
