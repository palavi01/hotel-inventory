package com.cts.hotel.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5335968268311446843L;
	private String message;
	private Throwable ex;

	public DataNotFoundException(String message, Throwable ex) {
		super(message, ex);
		this.message = message;
		this.ex = ex;
	}

	public DataNotFoundException(String message) {
		super(message);
		this.message = message;
	}
}