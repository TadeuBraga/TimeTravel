package br.com.tadeubraga.timetravel.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.tadeubraga.timetravel.exception.ApplicationException;
import br.com.tadeubraga.timetravel.model.Place;
import br.com.tadeubraga.timetravel.model.TimeTravel;
import br.com.tadeubraga.timetravel.repository.TimeTravelRepository;
import br.com.tadeubraga.timetravel.service.PlaceService;
import br.com.tadeubraga.timetravel.service.TimeTravelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TimeTravelServiceImpl implements TimeTravelService {
	private final TimeTravelRepository timeTravelRepository;
	private final PlaceService placeService;

	@Override
	public TimeTravel save(TimeTravel entity) {
		log.info("A new time travel has been received; timeTravel={}.", entity);
		validate(entity);
		initExistentPlace(entity);
		TimeTravel result = timeTravelRepository.save(entity);
		return result;
	}

	public void initExistentPlace(TimeTravel entity) {
		Optional<Place> placeDb = placeService.findByCityIgnoreCaseAndCountryIgnoreCase(entity.getPlace().getCity(),
				entity.getPlace().getCountry());
		if (placeDb.isPresent()) {
			entity.setPlace(placeDb.get());
			log.info("The referenced place was already present in database; timeTravel.place={}.", entity.getPlace());
		}
	}

	private Optional<TimeTravel> findByAllData(TimeTravel timeTravel) {
		return timeTravelRepository.findByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry(
				timeTravel.getPersonalGalaticId(), timeTravel.getDate(), timeTravel.getPlace().getCity(),
				timeTravel.getPlace().getCountry());
	}

	private void validate(TimeTravel entity) {
		if (findByAllData(entity).isPresent()) {
			log.error("What a weird time travel, avoiding paradox.");
			throw new ApplicationException("This person already traveled to this place in the same date.");
		}
	}

	@Override
	public List<TimeTravel> findAll() {
		log.info("Listing all existing time travels.");
		return timeTravelRepository.findAll();
	}

	@Override
	public TimeTravel findById(Long id) {
		log.info("Finding an existing time travel by id; timeTravel.id={}.", id);
		Optional<TimeTravel> timeTravelOp = timeTravelRepository.findById(id);
		if (timeTravelOp.isPresent()) {
			log.info("Existing time travel are going to be returned; timeTravel.id={}.", id);
			return timeTravelOp.get();
		} else {
			String message = "This time travel does not exist";
			log.error("{}; timeTravel.id={}", message, id);
			throw new ApplicationException(message);
		}
	}
}
