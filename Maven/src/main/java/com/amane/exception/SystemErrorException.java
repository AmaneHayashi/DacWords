package com.amane.exception;

public class SystemErrorException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SystemErrorException(String message) {
		super(message);
	}

	public SystemErrorException(String message, Throwable cause) {
		super(message, cause);
	}
}
