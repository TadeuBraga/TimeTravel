package br.com.tadeubraga.timetravel.controller.dto;

import java.util.Calendar;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.tadeubraga.timetravel.model.TimeTravel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeTravelDto {
	private Long id;
	@NotEmpty
	@Size(min = 5, max = 10)
	private String personalGalaticId;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	@NotNull
	private Calendar date;
	@NotNull
	@Valid
	private PlaceDto place;

	public TimeTravel toModel() {
		return TimeTravel.builder().personalGalaticId(personalGalaticId).date(date).place(place.toModel()).build();
	}

	public static TimeTravelDto ofModel(TimeTravel timeTravel) {
		return TimeTravelDto.builder().id(timeTravel.getId()).personalGalaticId(timeTravel.getPersonalGalaticId())
				.date(timeTravel.getDate()).place(PlaceDto.ofModel(timeTravel.getPlace())).build();
	}
}
