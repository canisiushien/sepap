/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.SourceFinancement;
import com.mfptps.appdgessddi.repositories.SourceFinancementRepository;
import com.mfptps.appdgessddi.service.SourceFinancementService;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Service
public class SourceFinancementServiceImpl implements SourceFinancementService {

    private final SourceFinancementRepository sourceFinancementRepository;

    public SourceFinancementServiceImpl(SourceFinancementRepository sourceFinancementRepository) {
        this.sourceFinancementRepository = sourceFinancementRepository;
    }

    @Override
    public SourceFinancement create(SourceFinancement sourceFinancement) {
        return sourceFinancementRepository.save(sourceFinancement);
    }

    @Override
    public SourceFinancement update(SourceFinancement sourceFinancement) {
        return sourceFinancementRepository.save(sourceFinancement);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourceFinancement> find(String libelle) {
        return sourceFinancementRepository.findByLibelle(libelle);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SourceFinancement> findAll(Pageable pageable) {
        return sourceFinancementRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        sourceFinancementRepository.deleteById(id);
    }

}
