package com.cts.hotel.exception;

public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -5335968268311446843L;
	private String message;

	public DataNotFoundException(String s) {
		super(s);
		this.message = s;
	}
}