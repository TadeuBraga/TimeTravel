package br.com.tadeubraga.timetravel.controller.dto;

import javax.validation.constraints.NotEmpty;

import br.com.tadeubraga.timetravel.model.Place;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlaceDto {
	private Long id;
	@NotEmpty
	private String city;
	@NotEmpty
	private String country;

	public Place toModel() {
		return Place.builder().city(city).country(country).build();
	}

	public static PlaceDto ofModel(Place place) {
		return PlaceDto.builder().id(place.getId()).city(place.getCity()).country(place.getCountry()).build();
	}
}
