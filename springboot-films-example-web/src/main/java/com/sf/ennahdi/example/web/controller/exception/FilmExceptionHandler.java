package com.sf.ennahdi.example.web.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sf.ennahdi.example.service.film.exception.FilmExistsAlreadyException;
import com.sf.ennahdi.example.service.film.exception.FilmNotFoundException;

@ControllerAdvice
public class FilmExceptionHandler extends ResponseEntityExceptionHandler {
	@ExceptionHandler(FilmNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleNotFoundException(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse("Server Error", ex.getLocalizedMessage());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(FilmExistsAlreadyException.class)
	public final ResponseEntity<ErrorResponse> handleExistsAlreadyException(Exception ex, WebRequest request) {
		ErrorResponse error = new ErrorResponse("Server Error", ex.getLocalizedMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.FOUND);
	}
}
