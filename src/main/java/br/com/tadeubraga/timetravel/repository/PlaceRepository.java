package br.com.tadeubraga.timetravel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.tadeubraga.timetravel.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
