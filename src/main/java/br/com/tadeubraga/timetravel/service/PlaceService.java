package br.com.tadeubraga.timetravel.service;

import java.util.Optional;

import br.com.tadeubraga.timetravel.model.Place;

public interface PlaceService {
	Optional<Place> findByCityIgnoreCaseAndCountryIgnoreCase(String city, String country);
}
