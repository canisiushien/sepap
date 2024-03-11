package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.CritereGouvernance;
import com.mfptps.appdgessddi.service.dto.CritereGouvernanceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CritereGouvernanceService {

    CritereGouvernance create(CritereGouvernanceDTO critereGouvernanceDTO);

    CritereGouvernance update(CritereGouvernance critereGouvernance);

    Optional<CritereGouvernance> get(Long id);

    Page<CritereGouvernance> findAll(Pageable pageable);

    Page<CritereGouvernance> findAllActifs(Pageable pageable);

}
