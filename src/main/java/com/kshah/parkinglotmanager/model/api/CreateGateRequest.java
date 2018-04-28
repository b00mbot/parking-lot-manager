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
@ApiModel(description = "Request Body for POST /api/v1/gates")
public class CreateGateRequest {

    @ApiModelProperty(value = "The initial status of the gate")
    private String status;

    @ApiModelProperty(value = "The user that is triggering the creation", position = 2)
    private String createUser;

    @ApiModelProperty(value = "The reason why a gate creation request is being made", position = 3)
    private String createReason;

}
