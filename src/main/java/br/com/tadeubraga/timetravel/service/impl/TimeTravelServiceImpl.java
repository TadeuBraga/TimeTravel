package br.com.tadeubraga.timetravel.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.tadeubraga.timetravel.model.TimeTravel;
import br.com.tadeubraga.timetravel.repository.TimeTravelRepository;
import br.com.tadeubraga.timetravel.service.TimeTravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TimeTravelServiceImpl implements TimeTravelService {
	private final TimeTravelRepository timeTravelRepository;

	@Override
	public TimeTravel save(TimeTravel entity) {
		validate(entity);
		return timeTravelRepository.save(entity);
	}

	private Optional<TimeTravel> findByAllData(TimeTravel timeTravel) {
		return timeTravelRepository.findByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry(
				timeTravel.getPersonalGalaticId(), timeTravel.getDate(), timeTravel.getPlace().getCity(),
				timeTravel.getPlace().getCountry());
	}

	private void validate(TimeTravel entity) {
		if (findByAllData(entity).isPresent()) {
			log.error("What a weird time travel, avoiding paradox.");
		}
	}

}
