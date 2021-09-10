package br.com.tadeubraga.timetravel.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	public ResponseEntity<TimeTravelDto> save(@RequestBody @Valid TimeTravelDto timeTravelDto) {
		var timeTravelDtoResult = TimeTravelDto.ofModel(timeTravelService.save(timeTravelDto.toModel()));
		return new ResponseEntity<TimeTravelDto>(timeTravelDtoResult, HttpStatus.CREATED);
	}

	@GetMapping
	public List<TimeTravelDto> findAll() {
		return timeTravelService.findAll().stream().map(TimeTravelDto::ofModel).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	public TimeTravelDto findById(@PathVariable Long id) {
		return TimeTravelDto.ofModel(timeTravelService.findById(id));
	}
	
	
}