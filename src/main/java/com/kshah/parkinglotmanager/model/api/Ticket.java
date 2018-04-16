package com.kshah.parkinglotmanager.model.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Ticket {

    @ApiModelProperty(required = true, value = "The parking ticket ID", position = 1)
    private String id;

    @ApiModelProperty(required = true, value = "The status of the parking ticket", position = 2)
    private String status;

    @ApiModelProperty(required = true, value = "The date that this parking ticket was issued", position = 3)
    private OffsetDateTime issueDate = null;

    @ApiModelProperty(required = true, value = "The gate which issued this parking ticket", position = 4)
    private Link gateIssuedAt;

    @ApiModelProperty(required = true, value = "The parking stall associated with this parking ticket", position = 5)
    private Link stall;

    @ApiModelProperty(required = true, value = "The date that this parking ticket was last updated", position = 6)
    private OffsetDateTime updateDate = null;

}