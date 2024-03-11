package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.service.dto.ObjectifDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ObjectifService {

    Objectif create(ObjectifDTO objectifDTO);

    Objectif update(Objectif objectif);

    Optional<Objectif> get(Long id);

    Optional<Objectif> get(String libelle);

    Page<Objectif> findByType(String type, Pageable pageable);

    Page<Objectif> findAll(Pageable pageable);

    void delete(Long id);
}
