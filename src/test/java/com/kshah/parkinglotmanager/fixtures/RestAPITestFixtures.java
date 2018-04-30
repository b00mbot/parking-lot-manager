package com.kshah.parkinglotmanager.fixtures;

import com.kshah.parkinglotmanager.model.api.*;
import com.kshah.parkinglotmanager.model.common.OperationStatus;

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
        IssueTicketRequest request = new IssueTicketRequest();
        request.setGateId(gateId);
        return request;
    }


    public static UpdateTicketRequest updateTicketRequest() {
        UpdateTicketRequest request = new UpdateTicketRequest();
        request.setAction(TicketUpdateAction.COMPLETE);
        return request;
    }

}
