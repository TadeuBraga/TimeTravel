package br.com.tadeubraga.timetravel.controller.dto;

import java.util.Calendar;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import br.com.tadeubraga.timetravel.model.TimeTravel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TimeTravelDto {
	private Long id;
	private String personalGalaticId;
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd")
	private Calendar date;
	private PlaceDto place;

	public TimeTravel toModel() {
		return TimeTravel.builder().personalGalaticId(personalGalaticId).date(date).place(place.toModel()).build();
	}

	public static TimeTravelDto ofModel(TimeTravel timeTravel) {
		return TimeTravelDto.builder().id(timeTravel.getId()).personalGalaticId(timeTravel.getPersonalGalaticId())
				.date(timeTravel.getDate()).place(PlaceDto.ofModel(timeTravel.getPlace())).build();
	}
}
