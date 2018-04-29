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
public class Error {

    @ApiModelProperty(required = true, value = "HTTP status code", position = 1)
    private Integer status;

    @ApiModelProperty(required = true, value = "Message for developers for debugging purposes", position = 2)
    private String developerMessage;

    @ApiModelProperty(required = true, value = "Message to display to an end-user", position = 3)
    private String userMessage;

    @ApiModelProperty(required = true, value = "Timestamp of when this error occurred", position = 4)
    private Instant timestamp;

}
