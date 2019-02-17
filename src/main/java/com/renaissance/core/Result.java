/**
 * 
 */
package com.renaissance.core;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Wilson
 */
public class Result<T> {

    @ApiModelProperty("响应状态")
    private String code;

    @ApiModelProperty("响应描述")
    private String message;

    @ApiModelProperty("响应内容")
    private T data;

    /**
     * The default constructor
     */
    public Result() {
        this.code = "1";
    }

    /**
     *
     * @param code
     */
    public Result(String code) {
        this.code = code;
    }
    
    /**
     * Initialize with code and message
     * 
     * @param code
     * @param message
     * @return
     */
    public Result initialize(String code, String message) {
        this.code = code;
        this.message = message;
        return this;
    }
    
    /**
     * Mark with success flag
     * 
     * @return
     */
    public Result success() {
        this.code = "0";
        return this;
    }
    
    /**
     * Mark with fail flag
     * 
     * @return
     */
    public Result fail() {
        this.code = "1";
        return this;
    }

    /**
     * Mark with fail flag
     *
     * @return
     */
    public Result fail(String code) {
        this.code = code;
        return this;
    }

    /**
     * Add message for result
     * 
     * @param message
     * @return
     */
    public Result message(String message) {
        this.message = message;
        return this;
    }
    
    /**
     * Add data for result
     * 
     * @param data
     * @return
     */
    public Result data(T data) {
        this.data = data;
        return this;
    }

    /**
     * Determine whether the status is valid
     * 
     * @return
     */
    @JsonIgnore
    public boolean isValid() {
        return StringUtils.equals(this.code, "0");
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }

	@Override
	public String toString() {
		return "Result [code=" + code + ", message=" + message + ", data=" + data + "]";
	}
    
}
