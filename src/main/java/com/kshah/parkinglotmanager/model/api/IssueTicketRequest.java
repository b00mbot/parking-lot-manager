package com.kshah.parkinglotmanager.model.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@ApiModel(description = "Request Body for POST /api/v1/tickets")
public class IssueTicketRequest {

    @NotNull(message = "The request body must contain a 'gateId' property")
    @ApiModelProperty(required = true, value = "The ID of the gate that is triggering the creation")
    private String gateId;

}
