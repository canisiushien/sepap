package com.mfptps.appdgessddi.service;

import java.util.*;

import com.mfptps.appdgessddi.entities.GrillePerformance;

import org.springframework.data.domain.*;

public interface GrillePerformanceService {
    
    GrillePerformance create(GrillePerformance grillePerformance);
    GrillePerformance update(GrillePerformance grillePerformance);
    Optional<GrillePerformance> get(Long id);
    Page<GrillePerformance> findAll(Pageable pageable);
    void delete(Long code);
}
