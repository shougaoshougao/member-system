package com.renaissance.core.web.filter;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Wilson
 */
public class LoggingRequestData implements Serializable {
	
	private static final long serialVersionUID = -4702574169916528738L;

	private Long userId;

	private String sender;

	private String method;

	private String path;

	private String postParam;

	private String getParam;

	private String headerJson;

	private String bodyJson;

    private String platform;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime time;

	public LoggingRequestData(Long userId, String sender, String method, String path, String postParam, String getParam, String headerJson, String bodyJson, LocalDateTime time, String platform) {
		this.userId = userId;
		this.sender = sender;
		this.method = method;
		this.path = path;
		this.postParam = postParam;
		this.getParam = getParam;
		this.headerJson = headerJson;
		this.bodyJson = bodyJson;
		this.time = time;
		this.platform = platform;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPostParam() {
		return postParam;
	}

	public void setPostParam(String postParam) {
		this.postParam = postParam;
	}

	public String getGetParam() {
		return getParam;
	}

	public void setGetParam(String getParam) {
		this.getParam = getParam;
	}

	public String getHeaderJson() {
		return headerJson;
	}

	public void setHeaderJson(String headerJson) {
		this.headerJson = headerJson;
	}

	public String getBodyJson() {
		return bodyJson;
	}

	public void setBodyJson(String bodyJson) {
		this.bodyJson = bodyJson;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
