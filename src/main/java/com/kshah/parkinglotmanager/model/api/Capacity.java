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
public class Capacity {

    @ApiModelProperty(required = true, value = "Current parking lot capacity", position = 1)
    private Integer current;

    @ApiModelProperty(required = true, value = "Maximum parking lot capacity", position = 2)
    private Integer max;

    @ApiModelProperty(required = true, value = "Timestamp of when this response was returned", position = 3)
    private Instant lastModified;

}
