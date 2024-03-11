package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.repositories.PonderationRepository;
import com.mfptps.appdgessddi.service.PonderationService;
import com.mfptps.appdgessddi.service.dto.PonderationDTO;
import com.mfptps.appdgessddi.service.mapper.PonderationMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PonderationServiceImpl  implements PonderationService {
    private final PonderationRepository ponderationRepository;
    private final PonderationMapper ponderationMapper ;

    public PonderationServiceImpl(PonderationRepository ponderationRepository, PonderationMapper ponderationMapper) {
        this.ponderationRepository = ponderationRepository;
        this.ponderationMapper = ponderationMapper;
    }


    @Override
    public Ponderation create(PonderationDTO ponderationDTO) {
        Ponderation ponderation =ponderationMapper.toEntity(ponderationDTO);

        return ponderationRepository.save(ponderation);
    }

    @Override
    public Ponderation update(Ponderation ponderation) {
        return ponderationRepository.save(ponderation);
    }

    @Override
    public Optional<Ponderation> get(Long id) {
        return ponderationRepository.findById(id);
    }

    @Override
    public Page<Ponderation> findAll(Pageable pageable) {
        return ponderationRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
  ponderationRepository.deleteById(id);
    }
}
