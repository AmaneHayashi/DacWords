package com.amane.exception;


public class WrongInputException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WrongInputException(String message) {
		super(message);
	}

	public WrongInputException(String message, Throwable cause) {
		super(message, cause);
	}
}