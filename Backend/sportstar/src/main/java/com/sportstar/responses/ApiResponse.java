package com.sportstar.responses;

public class ApiResponse {
	private Object responseObject;
	private String message;
	public ApiResponse(Object  responseObject,String message) {
		this.responseObject = responseObject;
		this.message = message;
	}
	public ApiResponse(Object  responseObject) {
		this.responseObject = responseObject;
	}

	public ApiResponse() {
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

}
