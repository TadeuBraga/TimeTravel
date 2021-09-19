package br.com.tadeubraga.timetravel.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.tadeubraga.timetravel.model.Place;
import br.com.tadeubraga.timetravel.repository.PlaceRepository;

@ExtendWith(MockitoExtension.class)
class PlaceServiceImplTest {
	@Mock
	PlaceRepository placeRepository;

	@InjectMocks
	PlaceServiceImpl placeServiceImpl;

	@Test
	void should_findByCityIgnoreCaseAndCountryIgnoreCase_When_ValidParametersAreGiven() {
		var place = Place.builder().id(1L).country("England").city("London").build();
		when(placeRepository.findByCityIgnoreCaseAndCountryIgnoreCase(place.getCity(), place.getCountry()))
				.thenReturn(Optional.of(place));
		var result = placeServiceImpl.findByCityIgnoreCaseAndCountryIgnoreCase(place.getCity(), place.getCountry());
		assertThat(result, equalTo(place));
	}

}
