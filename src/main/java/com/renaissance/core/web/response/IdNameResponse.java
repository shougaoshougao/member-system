package com.renaissance.core.web.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Wilson
 */
public class IdNameResponse {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    public IdNameResponse() {

    }

    public IdNameResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
