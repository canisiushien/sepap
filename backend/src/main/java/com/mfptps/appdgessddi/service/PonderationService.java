package com.mfptps.appdgessddi.service;


import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.service.dto.PonderationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PonderationService {


    Ponderation create(PonderationDTO ponderationDTO);

    Ponderation update(Ponderation ponderation);

    Optional<Ponderation> get(Long id);

    Page<Ponderation> findAll(Pageable pageable);

    void delete(Long id);
}
