package com.vvvv.common.exceptions;

public class ServiceExceptions extends Exception {
	private static final long serialVersionUID = -4936714864822685853L;
	public ServiceExceptions() {
	}
	public ServiceExceptions(String message) {
		super(message);
	}
	public ServiceExceptions(Throwable cause) {
		super(cause);
	}
	public ServiceExceptions(String message, Throwable cause) {
		super(message, cause);
	}
}
