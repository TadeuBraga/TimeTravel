package br.com.tadeubraga.timetravel.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.tadeubraga.timetravel.model.Place;
import br.com.tadeubraga.timetravel.repository.PlaceRepository;
import br.com.tadeubraga.timetravel.service.PlaceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements PlaceService {
	private final PlaceRepository placeRepository;
	@Override
	public Optional<Place> findByCityIgnoreCaseAndCountryIgnoreCase(String city, String country) {
		return placeRepository.findByCityIgnoreCaseAndCountryIgnoreCase(city, country);
	}

}
