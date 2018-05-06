package com.kshah.parkinglotmanager.fixtures;

import com.kshah.parkinglotmanager.model.api.CreateGateRequest;
import com.kshah.parkinglotmanager.model.api.Gate;
import com.kshah.parkinglotmanager.model.api.IssueTicketRequest;
import com.kshah.parkinglotmanager.model.api.Link;
import com.kshah.parkinglotmanager.model.api.Ticket;
import com.kshah.parkinglotmanager.model.api.TicketUpdateAction;
import com.kshah.parkinglotmanager.model.api.UpdateGateRequest;
import com.kshah.parkinglotmanager.model.api.UpdateTicketRequest;
import com.kshah.parkinglotmanager.model.common.OperationStatus;
import com.kshah.parkinglotmanager.model.common.TicketStatus;

import java.time.Instant;

public class RestAPITestFixtures {


    public static Gate createGate(String id, OperationStatus operationStatus) {
        Gate gate = new Gate();
        gate.setId(id);
        Instant now = Instant.now();
        gate.setCreated(now);
        gate.setLastModified(now);
        gate.setStatus(operationStatus);
        return gate;
    }


    public static Link createGateLink(String id) {
        Link link = new Link();
        link.setId(id);
        link.setLink("/api/v1/gates/" + id);
        return link;
    }


    public static Ticket createTicket(String id, String gateId, TicketStatus ticketStatus) {
        Ticket ticket = new Ticket();
        ticket.setId(id);
        Instant now = Instant.now();
        ticket.setIssued(now);
        ticket.setLastModified(now);
        ticket.setGateIssuedAt(createGateLink(gateId));
        ticket.setStatus(ticketStatus);
        return ticket;
    }


    public static Link createTicketLink(String id) {
        Link link = new Link();
        link.setId(id);
        link.setLink("/api/v1/tickets/" + id);
        return link;
    }


    public static CreateGateRequest createGateRequest(OperationStatus status, String createdBy, String createReason) {
        CreateGateRequest request = new CreateGateRequest();
        request.setStatus(status != null ? status.name() : null);
        request.setCreatedBy(createdBy);
        request.setCreateReason(createReason);
        return request;
    }

    public static UpdateGateRequest updateGateRequest(OperationStatus status, String modifiedBy, String modifyReason) {
        UpdateGateRequest request = new UpdateGateRequest();
        request.setStatus(status != null ? status.name() : null);
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
        request.setAction(TicketUpdateAction.COMPLETE.name());
        return request;
    }

}
