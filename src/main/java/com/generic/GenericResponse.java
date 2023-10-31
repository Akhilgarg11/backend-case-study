package com.generic;

public class GenericResponse<T> {
	private T data;
	private String message;
	private Boolean status;

	public GenericResponse(T data, String message, Boolean status) {
		this.data = data;
		this.status = status;
		this.message = message;
	}
	
	public GenericResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GenericResponse [data=" + data + ", message=" + message + ", status=" + status + "]";
	}

}
