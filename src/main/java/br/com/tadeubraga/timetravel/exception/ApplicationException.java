package br.com.tadeubraga.timetravel.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonIgnoreProperties(value = { "cause", "stackTrace", "suppressed" })
@NoArgsConstructor
public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final List<String> messages = new ArrayList<>();
	private final List<String> fields = new ArrayList<>();
	private HttpStatus status = HttpStatus.BAD_REQUEST;
	
	public ApplicationException(String message) {
		this.addMessage(message);
	}

	@Override
	public String getMessage() {
		if (!messages.isEmpty()) {
			if (messages.size() == fields.size()) {
				return IntStream.range(0, messages.size()).mapToObj(i -> fields.get(i) + ": " + messages.get(i))
						.collect(Collectors.joining(", "));
			} else {
				return this.messages.get(0);
			}
		}
		return null;
	}

	public void addMessage(String message) {
		this.messages.add(message);
	}
	
	public void addField(String field) {
		this.fields.add(field);
	}
}
