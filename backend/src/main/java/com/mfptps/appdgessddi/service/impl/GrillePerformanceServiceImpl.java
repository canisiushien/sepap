package com.mfptps.appdgessddi.service.impl;

import java.util.Optional;

import com.mfptps.appdgessddi.entities.GrillePerformance;
import com.mfptps.appdgessddi.repositories.GrillePerformanceRepository;
import com.mfptps.appdgessddi.service.GrillePerformanceService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GrillePerformanceServiceImpl implements GrillePerformanceService {

    private final GrillePerformanceRepository grillePerformanceRepository;

    public GrillePerformanceServiceImpl(GrillePerformanceRepository grillePerformanceRepository) {
        this.grillePerformanceRepository = grillePerformanceRepository;
    }

    @Override
    public GrillePerformance create(GrillePerformance grillePerformance) {
        return grillePerformanceRepository.save(grillePerformance);
    }

    @Override
    public GrillePerformance update(GrillePerformance grillePerformance) {
        return grillePerformanceRepository.save(grillePerformance);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GrillePerformance> get(Long id) {
        return grillePerformanceRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GrillePerformance> findAll(Pageable pageable) {
        return grillePerformanceRepository.findAll(pageable);
    }

    @Override
    public void delete(Long code) {    
        grillePerformanceRepository.deleteById(code);
    }
    
}
