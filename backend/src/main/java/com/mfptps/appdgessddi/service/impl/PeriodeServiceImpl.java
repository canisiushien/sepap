/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.entities.Periodicite;
import com.mfptps.appdgessddi.repositories.PeriodeRepository;
import com.mfptps.appdgessddi.repositories.PeriodiciteRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.PeriodeService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class PeriodeServiceImpl implements PeriodeService {

    private final PeriodeRepository periodeRepository;

    private final PeriodiciteRepository periodiciteRepository;

    public PeriodeServiceImpl(PeriodeRepository periodeRepository, PeriodiciteRepository periodiciteRepository) {
        this.periodeRepository = periodeRepository;
        this.periodiciteRepository = periodiciteRepository;
    }

    @Override
    public Periode create(Periode periode) {
        periodiciteRepository.findById(periode.getPeriodicite().getId())
                .filter(Periodicite::isActif)
                .orElseThrow(() -> new CustomException("Opération interdite ! Veuillez selectionner une périodicité active."));

        return periodeRepository.save(periode);
    }

    @Override
    public Periode update(Periode periode) {
        periodiciteRepository.findById(periode.getPeriodicite().getId())
                .filter(Periodicite::isActif)
                .orElseThrow(() -> new CustomException("Opération interdite ! Veuillez selectionner une périodicité active."));

        return periodeRepository.save(periode);
    }

    @Override
    public List<Periode> findByPeriodiciteActif() {
        return periodeRepository.findByPeriodiciteActif();
    }

    @Override
    public Optional<Periode> findByDateAndPeriodiciteActif(Date date) {
        //PREVIOUS A VALIDATOR_DATE
        return periodeRepository.findByDatePeriodiciteActif(date);
    }

}
