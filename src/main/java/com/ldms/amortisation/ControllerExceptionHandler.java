package com.ldms.amortisation;

import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler{

	@ExceptionHandler(ScheduleException.class)
	public  ResponseEntity<Object> handleScheduleException(Exception ex, WebRequest request){
		LocalDateTime now = LocalDateTime.now();
		ErrorDetails error = new ErrorDetails(now, ex.getMessage()); 
		return new ResponseEntity(error, HttpStatus.NOT_FOUND);
	}
}
