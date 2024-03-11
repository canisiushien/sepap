/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Impact; 
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author aboubacary
 */
public interface ImpactService {
    
    Impact create(Impact impact);

    Impact update(Impact impact);

    Optional<Impact> get(Long id); 

    Page<Impact> findMinisterImpacts(Long ministerId, Pageable pageable);
    
    Page<Impact> findMinisterImpacts(Long ministerId, boolean stats, Pageable pageable);

    void delete(Long id);
}
