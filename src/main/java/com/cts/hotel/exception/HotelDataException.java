package com.cts.hotel.exception;

public class HotelDataException extends RuntimeException {

	private static final long serialVersionUID = -5335968268311446843L;
	private String message;

	public HotelDataException(String s) {
		super(s);
		this.message = s;
	}
}