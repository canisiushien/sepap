package com.mfptps.appdgessddi.service;


import com.mfptps.appdgessddi.entities.Performer;
import com.mfptps.appdgessddi.service.dto.PerformanceDTO;
import com.mfptps.appdgessddi.service.dto.PerformerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PerformerService {

    Performer create(PerformerDTO performerDTO);

    Performer update(Performer performer);

    Optional<Performer> get(Long id);


    Page<Performer> findAll(Pageable pageable);

    void delete(Long id);
    
    public PerformanceDTO calculatePerformance(Long ministerId, Long structureId, Long exerciceId, Long userId);

}
