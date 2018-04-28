package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.model.common.TicketStatus;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ticket {

    @ApiModelProperty(required = true, value = "The parking ticket ID", position = 1)
    private String id;

    @ApiModelProperty(required = true, value = "The status of the parking ticket", position = 2)
    private TicketStatus status;

    @ApiModelProperty(required = true, value = "The date that this parking ticket was issued", position = 3)
    private Instant issued = null;

    @ApiModelProperty(required = true, value = "The gate which issued this parking ticket", position = 4)
    private Link gateIssuedAt;

    @ApiModelProperty(required = true, value = "The date that this parking ticket was last updated", position = 5)
    private Instant lastModified = null;

}
