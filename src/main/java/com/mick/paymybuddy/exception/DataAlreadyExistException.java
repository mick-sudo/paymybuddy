package com.mick.paymybuddy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataAlreadyExistException extends RuntimeException{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6320761683163893425L;

	public DataAlreadyExistException(String message){super(message);}
}
