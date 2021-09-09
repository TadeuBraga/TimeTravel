package br.com.tadeubraga.timetravel.repository;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tadeubraga.timetravel.model.TimeTravel;

public interface TimeTravelRepository extends JpaRepository<TimeTravel, Long> {
	Optional<TimeTravel> findByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry(String personalGalaticId,
			Calendar date, String cityName, String countryName);
}
