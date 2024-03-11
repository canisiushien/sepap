package com.mfptps.appdgessddi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mfptps.appdgessddi.entities.TypeActivites;
import com.mfptps.appdgessddi.service.dto.TypeActiviteDTO;


public interface TypeActivitesService {

	TypeActivites create(TypeActiviteDTO typeActivite);
	TypeActivites update(TypeActivites typeActivite);
	Optional<TypeActivites> get(Long id);
	Page<TypeActivites> findPage(Pageable pageable);
	void delete (Long id);
	
}
