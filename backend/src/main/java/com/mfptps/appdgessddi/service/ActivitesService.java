package com.mfptps.appdgessddi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.service.dto.ActivitesDTO;

public interface ActivitesService {
	
	Activites create(ActivitesDTO activites);
	Activites update(Activites activite);
	Optional<ActivitesDTO> get(Long id);
	Page<ActivitesDTO> findAll(Pageable pageable);
	void delete (Long id);

}
