package com.mfptps.appdgessddi.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.mfptps.appdgessddi.entities.IndicateurObjectif;

import com.mfptps.appdgessddi.service.dto.IndicateurObjectifDTO;

public interface IndicateurObjectifService {

	IndicateurObjectif create(IndicateurObjectifDTO indicateurObjectifDTO);
	IndicateurObjectif update(IndicateurObjectif indicateurObjectif);
	Optional<IndicateurObjectif> get(Long id);
//	Optional<IndicateurObjectif> getLibelle(String libelle);
	Page<IndicateurObjectif> findAll(Pageable pageable);
	void delete (Long id);
}
