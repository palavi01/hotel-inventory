package com.cts.hotel.exception;

public class HotelNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 589554050278665462L;
	private String message;
    private Throwable ex;

    public HotelNotFoundException( String message, Throwable ex) {
        super(message, ex);
        this.message = message;
        this.ex = ex;
    }

    public HotelNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}