/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.ParametrerImpact;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author aboubacary
 */
public interface ParametrerImpactService {
    
    ParametrerImpact create(ParametrerImpact data);

    ParametrerImpact update(ParametrerImpact data);

    Optional<ParametrerImpact> get(Long id); 

    Page<ParametrerImpact> findImpactParameter(Long ministerId,Long impactId, Pageable pageable);
    
    Page<ParametrerImpact> findExerciceParameter(Long ministerId,Long exercieId, Pageable pageable);

    void delete(Long id);
}
