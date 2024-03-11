package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.service.dto.ExerciceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExerciceService {

    Exercice update(ExerciceDTO exercice);

    void cloture();

    Optional<Exercice> get(Long id);

    Optional<Exercice> getByStatutAttente();

    Page<Exercice> findByStatut(ExerciceStatus statut, Pageable pageable);

    Page<Exercice> findAll(Pageable pageable);

    void delete(Long id);
}
