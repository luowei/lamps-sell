package com.vvvv.common.exceptions;

public class DaoExceptions extends Exception {
	private static final long serialVersionUID = -1462114185103590377L;

	public DaoExceptions() {
	}

	public DaoExceptions(String message) {
		super(message);
	}

	public DaoExceptions(Throwable cause) {
		super(cause);
	}

	public DaoExceptions(String message, Throwable cause) {
		super(message, cause);
	}

}
