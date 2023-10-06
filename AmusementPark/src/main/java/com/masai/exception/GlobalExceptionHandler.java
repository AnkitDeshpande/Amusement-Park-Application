package com.masai.exception;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(SomethingWentWrongException.class)
	public ResponseEntity<ErrorDetails> cust(SomethingWentWrongException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InsufficientBalanceException.class)
	public ResponseEntity<ErrorDetails> cust(InsufficientBalanceException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.BAD_GATEWAY);
	}

	@ExceptionHandler(ParkNotFoundException.class)
	public ResponseEntity<ErrorDetails> cust(ParkNotFoundException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ActivityNotFoundException.class)
	public ResponseEntity<ErrorDetails> cust(ActivityNotFoundException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDetails> cust(UserNotFoundException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(TicketNotFoundException.class)
	public ResponseEntity<ErrorDetails> cust(TicketNotFoundException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<ErrorDetails> cust(ReviewNotFoundException ex, WebRequest req) {
		ErrorDetails ed = new ErrorDetails(req.getDescription(false), ex.getMessage(), LocalDate.now());
		return new ResponseEntity<ErrorDetails>(ed, HttpStatus.NOT_FOUND);
	}

}
