package com.kshah.parkinglotmanager.model.api;

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
public class Stall {

    @ApiModelProperty(required = true, value = "The parking stall ID", position = 1)
    private String id;

    @ApiModelProperty(required = true, value = "The status of the parking stall", position = 2)
    private String status = null;

    @ApiModelProperty(value = "The associated parking ticket for the parking stall", position = 3)
    private Link ticket = null;

    @ApiModelProperty(required = true, value = "The date that this parking stall was created", position = 4)
    private Instant createDate = null;

    @ApiModelProperty(required = true, value = "The date that this parking stall was last updated", position = 5)
    private Instant updateDate = null;

}

