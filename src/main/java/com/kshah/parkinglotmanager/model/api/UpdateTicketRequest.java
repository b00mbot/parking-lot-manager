package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.validators.EnumeratedString;
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
@ApiModel(description = "Request Body for PATCH /api/v1/tickets/{id}")
public class UpdateTicketRequest {

    @EnumeratedString(enumClass = TicketUpdateAction.class, message = "Acceptable values for 'action': COMPLETE")
    @NotNull(message = "The request body must contain an 'action' property")
    @ApiModelProperty(required = true, value = "The update action to take on this parking ticket")
    private String action;

}
