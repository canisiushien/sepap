/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.repositories.ParametrerImpactRepository;
import com.mfptps.appdgessddi.entities.ParametrerImpact;
import com.mfptps.appdgessddi.service.ParametrerImpactService;
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
public class ParametrerImpactServiceImpl implements ParametrerImpactService {
    
    private final ParametrerImpactRepository repository;

    public ParametrerImpactServiceImpl(ParametrerImpactRepository repository){
        this.repository = repository;
    }

    @Override
    public ParametrerImpact create(ParametrerImpact data) {
        return repository.save(data);
    }

    @Override
    public ParametrerImpact update(ParametrerImpact data) {
        return repository.save(data);
    }

    @Override
    public Optional<ParametrerImpact> get(Long id) {
       return repository.findById(id);
    }

    @Override
    public Page<ParametrerImpact> findImpactParameter(Long ministerId, Long impactId, Pageable pageable) {
        return repository.findAllMinisterImpact(ministerId, impactId, pageable);
    }

    @Override
    public Page<ParametrerImpact> findExerciceParameter(Long ministerId, Long exercieId, Pageable pageable) {
        return repository.findExerciceImpact(ministerId, exercieId, pageable);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
    
}
