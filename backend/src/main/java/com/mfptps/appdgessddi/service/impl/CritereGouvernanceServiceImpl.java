package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.CritereGouvernance;
import com.mfptps.appdgessddi.repositories.CritereGouvernanceRepository;
import com.mfptps.appdgessddi.service.CritereGouvernanceService;
import com.mfptps.appdgessddi.service.dto.CritereGouvernanceDTO;
import com.mfptps.appdgessddi.service.mapper.CritereGouvernanceMapper;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class CritereGouvernanceServiceImpl implements CritereGouvernanceService {

    private final CritereGouvernanceRepository critereGouvernanceRepository;
    private final CritereGouvernanceMapper critereGouvernanceMapper;

    public CritereGouvernanceServiceImpl(CritereGouvernanceRepository critereGouvernanceRepository, CritereGouvernanceMapper critereGouvernanceMapper) {
        this.critereGouvernanceRepository = critereGouvernanceRepository;
        this.critereGouvernanceMapper = critereGouvernanceMapper;
    }

    @Override
    public CritereGouvernance create(CritereGouvernanceDTO critereGouvernanceDTO) {

        CritereGouvernance critereGouvernance = critereGouvernanceMapper.toEntity(critereGouvernanceDTO);

        return critereGouvernanceRepository.save(critereGouvernance);
    }

    @Override
    public CritereGouvernance update(CritereGouvernance critereGouvernance) {
        return critereGouvernanceRepository.save(critereGouvernance);
    }

    @Override
    public Optional<CritereGouvernance> get(Long id) {
        return critereGouvernanceRepository.findById(id);
    }

    @Override
    public Page<CritereGouvernance> findAll(Pageable pageable) {
        return critereGouvernanceRepository.findAll(pageable);
    }

    @Override
    public Page<CritereGouvernance> findAllActifs(Pageable pageable) {
        return critereGouvernanceRepository.findByActifTrue(pageable);
    }
}
