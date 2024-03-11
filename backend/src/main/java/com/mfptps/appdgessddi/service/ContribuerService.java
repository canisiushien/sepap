/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.Contribuer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *
 * @author aboubacary
 */
public interface ContribuerService {
    
    Contribuer create(Contribuer data);

    Contribuer update(Contribuer data);

    Optional<Contribuer> get(Long id); 

    Page<Contribuer> findStructureContribuer(Long exerciceId, Long structureId, Pageable pageable);
    
    Page<Contribuer> findMinisterContribuer(Long ministerId, Long exerciceId, Pageable pageable);

    void delete(Long id);
}
