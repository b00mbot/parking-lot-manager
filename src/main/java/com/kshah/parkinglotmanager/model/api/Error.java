package com.kshah.parkinglotmanager.model.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Error {

    @ApiModelProperty(required = true, value = "HTTP status code", position = 1)
    private Integer status;

    @ApiModelProperty(required = true, value = "Description of the nature of the error", position = 2)
    private String description;

    @ApiModelProperty(value = "Additional error details", position = 3)
    private List<ErrorDetail> errors;

    @ApiModelProperty(required = true, value = "Timestamp of when this error occurred", position = 4)
    private Instant timestamp;

}
