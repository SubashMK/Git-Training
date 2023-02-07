package com.stg.exception;

@SuppressWarnings("serial")
public class BookingException extends Exception {

	private String errormessageString;

	public BookingException(String errormessageString) {
		super();
		this.errormessageString = errormessageString;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.errormessageString;
	}

}
