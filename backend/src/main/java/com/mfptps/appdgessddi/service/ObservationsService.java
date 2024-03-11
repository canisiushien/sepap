package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Observations;
import com.mfptps.appdgessddi.service.dto.ObservationsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ObservationsService {
    Observations create(ObservationsDTO observationsDTO);

    Observations update(Observations observations);

    Optional<Observations> get(Long id);


    Page<Observations> findAll(Pageable pageable);

    void delete(Long id);
}
