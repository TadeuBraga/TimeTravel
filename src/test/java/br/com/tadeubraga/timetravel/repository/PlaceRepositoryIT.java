package br.com.tadeubraga.timetravel.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.tadeubraga.timetravel.model.Place;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PlaceRepositoryIT {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	PlaceRepository placeRepository;

	@Test
	void should_findByCityIgnoreCaseAndCountryIgnoreCase_When_ValidParametersAreGiven() {
		var place = Place.builder().country("England").city("London").build();
		entityManager.persist(place);
		entityManager.flush();

		Optional<Place> placeOp = placeRepository.findByCityIgnoreCaseAndCountryIgnoreCase(
				place.getCity().toLowerCase(), place.getCountry().toLowerCase());

		assertAll(() -> assertThat(placeOp.isPresent(), equalTo(true)),
				() -> assertThat(placeOp.get().getCity(), equalTo(place.getCity())),
				() -> assertThat(placeOp.get().getCountry(), equalTo(place.getCountry())));
	}

}
