/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Periodicite;
import com.mfptps.appdgessddi.repositories.PeriodiciteRepository;
import com.mfptps.appdgessddi.service.PeriodiciteService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@Service
public class PeriodiciteServiceImpl implements PeriodiciteService {

    private final PeriodiciteRepository periodiciteRepository;

    public PeriodiciteServiceImpl(PeriodiciteRepository periodiciteRepository) {
        this.periodiciteRepository = periodiciteRepository;
    }

    @Override
    public Periodicite create(Periodicite periodicite) {
//        if (!periodicite.isActif()) {
//            return periodiciteRepository.save(periodicite);
//        } else {
        Periodicite existingActif = periodiciteRepository.findByActifTrue().orElse(null);
        if (existingActif == null) {
            return periodiciteRepository.save(periodicite);
        } else {
            existingActif.setActif(false);
            existingActif.setDeleted(true);
            periodiciteRepository.save(existingActif);
            return periodiciteRepository.save(periodicite);
        }
//        }
    }

    @Override
    public Optional<Periodicite> getActif() {
        return periodiciteRepository.findByActifTrue();
    }

    @Override
    public Page<Periodicite> findAll(Pageable pageable) {
        return periodiciteRepository.findAll(pageable);
    }

}
