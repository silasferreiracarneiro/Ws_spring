package com.silas.wsspring.resources.exception;

import java.io.Serializable;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer status;
	private String msg;
	private Long timeSpan;
	
	public StandardError(Integer status, String msg, Long timeSpan) {
		super();
		this.status = status;
		this.msg = msg;
		this.timeSpan = timeSpan;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTimeSpan() {
		return timeSpan;
	}

	public void setTimeSpan(Long timeSpan) {
		this.timeSpan = timeSpan;
	}
	
	
}
