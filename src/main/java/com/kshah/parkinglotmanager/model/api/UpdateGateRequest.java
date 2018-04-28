package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.model.common.OperationStatus;
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
@ApiModel(description = "Request Body for PATCH /api/v1/gates/{id}")
public class UpdateGateRequest {

    @ApiModelProperty(required = true, value = "The status to update to for the gate", position = 1)
    private OperationStatus status;

    @ApiModelProperty(value = "The user that is triggering the update", position = 2)
    private String modifiedBy;

    @ApiModelProperty(value = "A reason as to why the update is being made", position = 3)
    private String modifyReason;

}
