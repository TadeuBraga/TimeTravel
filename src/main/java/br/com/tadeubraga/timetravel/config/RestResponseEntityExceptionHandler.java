package br.com.tadeubraga.timetravel.config;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.tadeubraga.timetravel.exception.ApplicationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<FieldError> errors = ex.getBindingResult().getFieldErrors();
		ApplicationException exception = new ApplicationException();
		for (FieldError error : errors) {
			exception.addMessage(error.getDefaultMessage());
			exception.addField(error.getField());
		}
		return handleApplicationException(exception, request);
	}
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<Object> handleApplicationException(ApplicationException ex, WebRequest request) {
		return new ResponseEntity<Object>(ex, ex.getStatus());
	}
}
