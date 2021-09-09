package br.com.tadeubraga.timetravel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tadeubraga.timetravel.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
	public Optional<Place> findByCityIgnoreCaseAndCountryIgnoreCase(String city, String country);
}
