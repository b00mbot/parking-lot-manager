package com.kshah.parkinglotmanager.model.api;

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

    @ApiModelProperty(required = true, value = "The update action to take on this parking ticket")
    private TicketUpdateAction action;

}
