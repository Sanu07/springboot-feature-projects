package com.test.exception;

import org.springframework.http.HttpStatus;

public class MyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus httpStatus;
	
	public MyException(String message) {
		this.message = message;
		this.httpStatus = getHttpStatus().INTERNAL_SERVER_ERROR;
	}
	
	public MyException(String message, HttpStatus httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	@Override
	public String toString() {
		return "MyException [message=" + message + "]";
	}
}
