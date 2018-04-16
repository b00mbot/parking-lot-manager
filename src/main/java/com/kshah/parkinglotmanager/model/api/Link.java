package com.kshah.parkinglotmanager.model.api;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Link {

    @ApiModelProperty(required = true, value = "ID of resource", position = 1)
    private String id;

    @ApiModelProperty(required = true, value = "Link to resource", position = 2)
    private String link = null;

}
