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
@ApiModel(description = "Request Body for PATCH /api/v1/stalls/{id}")
public class UpdateStallRequest {

    @ApiModelProperty(required = true, value = "The status to update to for the parking stall", position = 1)
    private String status;

    @ApiModelProperty(value = "A reason as to why the update is being made", position = 2)
    private String updateReason;

}
