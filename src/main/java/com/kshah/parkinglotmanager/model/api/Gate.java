package com.kshah.parkinglotmanager.model.api;

import com.kshah.parkinglotmanager.model.common.OperationStatus;
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
public class Gate {

    @ApiModelProperty(required = true, value = "The gate ID", position = 1)
    private String id;

    @ApiModelProperty(required = true, value = "The status of the gate", position = 2)
    private OperationStatus status;

    @ApiModelProperty(required = true, value = "The date that this gate was created", position = 3)
    private Instant created = null;

    @ApiModelProperty(required = true, value = "The date that this gate was last updated", position = 4)
    private Instant lastModified = null;

}
