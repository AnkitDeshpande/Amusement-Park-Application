package com.masai.exception;

import java.time.LocalDate;

public class ErrorDetails {
	private String url;
	private String msg;
	private LocalDate time;

	public ErrorDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ErrorDetails(String url, String msg, LocalDate time) {
		super();
		this.url = url;
		this.msg = msg;
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public LocalDate getTime() {
		return time;
	}

	public void setTime(LocalDate time) {
		this.time = time;
	}

}
