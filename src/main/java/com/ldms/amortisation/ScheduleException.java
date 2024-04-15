package com.ldms.amortisation;

public class ScheduleException extends RuntimeException {

	private String message;

	public ScheduleException(String message) {
	    super(message);
	}
}
