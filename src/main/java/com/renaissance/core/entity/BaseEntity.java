package com.renaissance.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author Wilson
 *
 * @param <T>
 */
@MappedSuperclass
public class BaseEntity<T>{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime createTime;

    protected Long creatorId;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	protected LocalDateTime lastUpdateTime;

    protected Long lastUpdateId;

	@ColumnDefault(value = "0")
    protected boolean deleted;

    public T initialize(Long operatorId) {
    	LocalDateTime now = LocalDateTime.now();
    	this.createTime = now;
    	this.lastUpdateTime = now;
    	this.deleted = false;
    	this.creatorId = operatorId;
    	this.lastUpdateId = operatorId;
    	return (T)this;
    }

    public T initialize() {
    	return initialize(null);
    }

    public T recordEdit(Long operatorId) {
		LocalDateTime now = LocalDateTime.now();
    	this.lastUpdateTime = now;
    	this.lastUpdateId = operatorId;
    	return (T)this;
    }

    public T delete(Long operatorId) {
		LocalDateTime now = LocalDateTime.now();
    	this.lastUpdateTime = now;
    	this.lastUpdateId = operatorId;
    	this.deleted = true;
    	return (T)this;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public LocalDateTime getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDateTime lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public Long getLastUpdateId() {
		return lastUpdateId;
	}

	public void setLastUpdateId(Long lastUpdateId) {
		this.lastUpdateId = lastUpdateId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}