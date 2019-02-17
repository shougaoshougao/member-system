package com.renaissance.core.web.response;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Wilson
 */
public class NameDescResponse {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String desc;

    public NameDescResponse() {

    }

    public NameDescResponse(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
