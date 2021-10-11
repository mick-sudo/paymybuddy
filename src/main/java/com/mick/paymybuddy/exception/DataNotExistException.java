package com.mick.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataNotExistException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -582616643185311224L;

	public DataNotExistException(String message){super(message);}
}
