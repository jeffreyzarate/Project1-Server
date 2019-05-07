package com.revature.util;

public class HttpException extends RuntimeException {
	private int status;
	
	public HttpException(int statusCode) {
		this.status = statusCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
}