package br.com.tadeubraga.timetravel.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.tadeubraga.timetravel.controller.dto.TimeTravelDto;
import br.com.tadeubraga.timetravel.service.TimeTravelService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/time-travels")
@RequiredArgsConstructor
public class TimeTravelController {
	private final TimeTravelService timeTravelService;

	@PostMapping
	public ResponseEntity<TimeTravelDto> save(@RequestBody TimeTravelDto timeTravelDto) {
		var timeTravelDtoResult = TimeTravelDto.ofModel(timeTravelService.save(timeTravelDto.toModel()));
		return new ResponseEntity<TimeTravelDto>(timeTravelDtoResult, HttpStatus.CREATED);
	}
}