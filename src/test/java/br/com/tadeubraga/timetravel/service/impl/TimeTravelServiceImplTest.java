package br.com.tadeubraga.timetravel.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.SerializationUtils;

import br.com.tadeubraga.timetravel.exception.ApplicationException;
import br.com.tadeubraga.timetravel.model.Place;
import br.com.tadeubraga.timetravel.model.TimeTravel;
import br.com.tadeubraga.timetravel.repository.TimeTravelRepository;
import br.com.tadeubraga.timetravel.service.PlaceService;

@ExtendWith(MockitoExtension.class)
class TimeTravelServiceImplTest {
	@Mock
	TimeTravelRepository timeTravelRepository;

	@Mock
	PlaceService placeService;

	@InjectMocks
	TimeTravelServiceImpl timeTravelServiceImpl;

	private TimeTravel timeTravel;

	@BeforeEach
	void setUpEach() {
		timeTravel = TimeTravel.builder().personalGalaticId("matrix68").date(Calendar.getInstance())
				.place(Place.builder().city("London").country("UK").build()).build();
	}

	@Test
	void should_NotSave_When_ThereIsAnotherTravelWithSameParams() {
		when(timeTravelRepository.findByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry(
				timeTravel.getPersonalGalaticId(), timeTravel.getDate(), timeTravel.getPlace().getCity(),
				timeTravel.getPlace().getCountry())).thenReturn(Optional.of(timeTravel));
		assertThrows(ApplicationException.class, () -> timeTravelServiceImpl.save(timeTravel));
	}

	@Test
	void should_Save_When_PlaceExistsInDatabase() {
		var cityId = 1L;
		var placeDb = Optional
				.of((Place) SerializationUtils.deserialize(SerializationUtils.serialize(timeTravel.getPlace())));
		placeDb.get().setId(cityId);
		when(placeService.findByCityIgnoreCaseAndCountryIgnoreCase(timeTravel.getPlace().getCity(),
				timeTravel.getPlace().getCountry())).thenReturn(placeDb);
		timeTravelServiceImpl.save(timeTravel);
		assertAll(() -> assertThat(timeTravel.getPlace().getId(), equalTo(cityId)),
				() -> verify(timeTravelRepository).save(timeTravel));
	}

	@Test
	void should_Save_When_PlaceIsTotallyNew() {
		TimeTravel timeTravelDb = (TimeTravel) SerializationUtils.deserialize(SerializationUtils.serialize(timeTravel));
		timeTravelDb.setId(1L);
		when(timeTravelRepository.save(timeTravel)).thenReturn(timeTravelDb);
		assertThat(timeTravelServiceImpl.save(timeTravel), equalTo(timeTravelDb));

	}

}
