package com.mfptps.appdgessddi.service;


import com.mfptps.appdgessddi.entities.Parametre;
import com.mfptps.appdgessddi.service.dto.ParametreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;


public interface ParametreService {

    Parametre create(ParametreDTO parametreDTO);

    Parametre update(Parametre parametre);

    Page<Parametre> findAll(Pageable pageable);

    Optional<Parametre> get(Long id);

    void delete(Long id);
}
