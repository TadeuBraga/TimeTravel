package br.com.tadeubraga.timetravel.service;

import java.util.List;

import br.com.tadeubraga.timetravel.model.TimeTravel;

public interface TimeTravelService {
	TimeTravel save(TimeTravel entity);

	List<TimeTravel> findAll();
	
	TimeTravel findById(Long id);
}
