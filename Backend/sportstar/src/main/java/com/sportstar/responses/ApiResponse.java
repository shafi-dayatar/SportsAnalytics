package com.sportstar.responses;

public class ApiResponse {
	private Object responseObject;
	public ApiResponse(Object  responseObject) {
		this.responseObject = responseObject;
	}

	public ApiResponse() {
	}

	public Object getResponseObject() {
		return responseObject;
	}
	public void setResponseObject(Object responseObject) {
		this.responseObject = responseObject;
	}

}
