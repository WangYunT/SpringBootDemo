package com.example.demo.exception;

public class BusinessException extends RuntimeException {

	/**
	 *
	 */
	private static final long serialVersionUID = -5717085985703058952L;

	private String status;

	private String args;

	public BusinessException(String status, String args) {
		this.args = args;
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

}
