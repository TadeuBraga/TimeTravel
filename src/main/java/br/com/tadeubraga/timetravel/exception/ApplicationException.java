package br.com.tadeubraga.timetravel.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonIgnoreProperties(value = {"cause", "stackTrace", "suppressed"})
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private String message;
	@Builder.Default
	private HttpStatus status = HttpStatus.BAD_REQUEST;
}
