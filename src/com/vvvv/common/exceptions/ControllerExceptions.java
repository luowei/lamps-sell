package com.vvvv.common.exceptions;

public class ControllerExceptions extends Exception {

	private static final long serialVersionUID = -7182966590039078445L;

	public ControllerExceptions() {
	}

	public ControllerExceptions(String message) {
		super(message);
	}

	public ControllerExceptions(Throwable cause) {
		super(cause);
	}

	public ControllerExceptions(String message, Throwable cause) {
		super(message, cause);
	}
}
