/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Contribuer;
import com.mfptps.appdgessddi.repositories.ContribuerRepository;
import com.mfptps.appdgessddi.service.ContribuerService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 *
 * @author aboubacary
 */
@Slf4j
@Service
public class ContribuerServiceImpl implements ContribuerService {
    
    private final ContribuerRepository repository;
    
    private final ParametrerImpactServiceImpl impactRepository;

    public ContribuerServiceImpl(ContribuerRepository repository, ParametrerImpactServiceImpl impactRepository){
        this.repository = repository;
        this.impactRepository = impactRepository;
    }

    @Override
    public Contribuer create(Contribuer data) {
        // recherche
        //ParametrerImpact parametrer = data.getParametrerImpact();
        
       return repository.save(data);
    }

    @Override
    public Contribuer update(Contribuer data) {
        return repository.save(data);
    }

    @Override
    public Optional<Contribuer> get(Long id) {
        return repository.findById(id);
    }

    @Override
    public Page<Contribuer> findStructureContribuer(Long exerciceId, Long structureId, Pageable pageable) {
        return repository.findStructureContribution(structureId, exerciceId, pageable);
    }

    @Override
    public Page<Contribuer> findMinisterContribuer(Long ministerId, Long exerciceId, Pageable pageable) {
        return repository.findExerciceContribution(ministerId, exerciceId, pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    } 
    
}
