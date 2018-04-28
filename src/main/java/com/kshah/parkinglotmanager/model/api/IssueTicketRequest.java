package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.model.common.TicketStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@ApiModel(description = "Request Body for POST /api/v1/tickets")
public class IssueTicketRequest {

    @ApiModelProperty(value = "The initial status of the ticket")
    private TicketStatus status;

    @ApiModelProperty(value = "The user that is triggering the creation", position = 2)
    private String createdBy;

    @ApiModelProperty(value = "The reason why a ticket issue request is being made", position = 3)
    private String createReason;

}
