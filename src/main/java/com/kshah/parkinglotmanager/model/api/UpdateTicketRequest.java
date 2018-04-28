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
@ApiModel(description = "Request Body for PATCH /api/v1/tickets/{id}")
public class UpdateTicketRequest {

    @ApiModelProperty(required = true, value = "The status to update to for the parking ticket", position = 1)
    private TicketStatus status;

    @ApiModelProperty(value = "The user that is triggering the update", position = 2)
    private String modifiedBy;

    @ApiModelProperty(value = "A reason as to why the update is being made", position = 3)
    private String modifyReason;

}
