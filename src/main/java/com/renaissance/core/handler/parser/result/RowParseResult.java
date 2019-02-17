package com.renaissance.core.handler.parser.result;

/**
 * @author Wilson
 */
public class RowParseResult {

	/** 第几行 */
	private int row;

	/** 详细信息 */
	private String message = "";

	/** 状态 */
	private boolean success;

	public RowParseResult() {
		this.success = true;
	}

	public RowParseResult(int row) {
		this.row = row;
		this.success = true;
	}

	public RowParseResult(int row, boolean success) {
	    this.row = row;
	    this.success = success;
	}

	/**
	 * @param message
	 */
	public RowParseResult populateMessage(String message) {
		this.setMessage(this.message == null ? message : this.message+message);
		return this;
	}

	public RowParseResult success() {
		this.success = true;
		return this;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
