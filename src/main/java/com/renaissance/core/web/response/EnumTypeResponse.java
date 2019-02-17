package com.renaissance.core.web.response;

import com.renaissance.core.utils.EnumUtils;
import io.swagger.annotations.ApiModelProperty;

import java.util.Map;

public class EnumTypeResponse {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("序号")
    private int ordinal;

    public EnumTypeResponse() {}

    public EnumTypeResponse(Enum en) {
        Map<String, Object> descriptionMap = EnumUtils.describe(en);
        this.name = descriptionMap.get("name").toString();
        this.description = descriptionMap.get("description").toString();
        this.ordinal = Integer.valueOf(descriptionMap.get("ordinal").toString());
    }

    public EnumTypeResponse(String name, String description, int ordinal) {
        this.name = name;
        this.description = description;
        this.ordinal = ordinal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }
}
