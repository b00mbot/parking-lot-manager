package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.model.common.OperationStatus;
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
@ApiModel(description = "Request Body for PATCH /api/v1/gates/{id}")
public class UpdateGateRequest {

    @EnumeratedString(enumClass = OperationStatus.class, message = "Acceptable values for 'status': READY_FOR_OPERATION, OPERATIONAL, CLOSED_FOR_MAINTENANCE, CLOSED_PERMANENTLY")
    @NotNull(message = "The request body must contain a 'status' property")
    @ApiModelProperty(required = true, value = "The status to update to for the gate", position = 1)
    private String status;

    @ApiModelProperty(value = "The user that is triggering the update", position = 2)
    private String modifiedBy;

    @ApiModelProperty(value = "A reason as to why the update is being made", position = 3)
    private String modifyReason;

}
