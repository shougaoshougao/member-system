package com.renaissance.core.handler.parser.result;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Wilson
 */
public class ParseResult {

	private Date createTime;
	
	private List<RowParseResult> items = new ArrayList<RowParseResult>();
	
	private int totalCount;
	
	private int successCount;
	
	private int failCount;
	
	private int repeatCount;

	/**
	 * default constructor
	 */
	public ParseResult() {

	}

	public ParseResult(List<RowParseResult> items, int successCount, int failCount, int repeatCount) {
		
		this.items = items;
		this.createTime = new Date();
		this.successCount = successCount;
		this.failCount = failCount;
		this.repeatCount = repeatCount;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<RowParseResult> getItems() {
		return items;
	}

	public void setItems(List<RowParseResult> items) {
		this.items = items;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}

	public int getFailCount() {
		return failCount;
	}

	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}

	public int getRepeatCount(){
		return repeatCount;
	}

	public void setRepeatCount(int repeartCount) {
		this.repeatCount = repeartCount;
	}
	
}
