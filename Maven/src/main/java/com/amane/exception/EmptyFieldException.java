package com.amane.exception;

public class EmptyFieldException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmptyFieldException(String message) {
		super(message);
	}

	public EmptyFieldException(String message, Throwable cause) {
		super(message, cause);
	}
}
