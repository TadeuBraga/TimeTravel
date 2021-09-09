package br.com.tadeubraga.timetravel.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tadeubraga.timetravel.exception.ApplicationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ApplicationException> handleApplicationException(ApplicationException ex, WebRequest request) {
		return new ResponseEntity<ApplicationException>(ex, ex.getStatus());
	}
}
