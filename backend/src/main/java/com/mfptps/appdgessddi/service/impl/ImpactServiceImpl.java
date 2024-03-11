/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Impact;
import com.mfptps.appdgessddi.repositories.ImpactRepository;
import com.mfptps.appdgessddi.service.ImpactService;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author aboubacary
 */
@Slf4j
@Service
@Transactional
public class ImpactServiceImpl implements ImpactService {

    private final ImpactRepository impactRepository;

    public ImpactServiceImpl(ImpactRepository impactRepository){
        this.impactRepository = impactRepository;
    }
    
    @Override
    public Impact create(Impact impact) { 
        return impactRepository.save(impact);
    }

    @Override
    public Impact update(Impact impact) {
        return impactRepository.save(impact);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Impact> get(Long id) {
        return impactRepository.findById(id);
    }

    @Override
    public Page<Impact> findMinisterImpacts(Long ministerId, Pageable pageable) {
        return impactRepository.findAllMinisterImpact(ministerId, pageable);
    }
    
    @Override
    public Page<Impact> findMinisterImpacts(Long ministerId, boolean stats, Pageable pageable) {
        return impactRepository.findMinisterImpact(ministerId,stats , pageable);
    }

    @Override
    public void delete(Long id) {
        impactRepository.deleteById(id);
    }
    
}
