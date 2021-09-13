package br.com.tadeubraga.timetravel.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tadeubraga.timetravel.model.Place;
import br.com.tadeubraga.timetravel.model.TimeTravel;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class TimeTravelRepositoryIT {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	TimeTravelRepository timeTravelRepository;

	@Test
	void should_FindByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry_When_ValidParametersAreGiven() {
		var personalGalaticId = "nick76";
		var now = Calendar.getInstance();
		var place = Place.builder().country("England").city("London").build();
		TimeTravel t = TimeTravel.builder().personalGalaticId(personalGalaticId).date(now).place(place).build();
		entityManager.persist(t);
		entityManager.flush();

		Optional<TimeTravel> timeTravelOp = timeTravelRepository
				.findByPersonalGalaticIdAndDateAndPlaceCityAndPlaceCountry(personalGalaticId, now, place.getCity(),
						place.getCountry());

		assertAll(() -> assertThat(timeTravelOp.isPresent(), equalTo(true)),
				() -> assertThat(timeTravelOp.get().getPersonalGalaticId(), equalTo(t.getPersonalGalaticId())),
				() -> assertThat(timeTravelOp.get().getDate(), equalTo(t.getDate())),
				() -> assertThat(timeTravelOp.get().getPlace().getCity(), equalTo(t.getPlace().getCity())),
				() -> assertThat(timeTravelOp.get().getPlace().getCountry(), equalTo(t.getPlace().getCountry())));
	}

}
