package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Observations;
import com.mfptps.appdgessddi.repositories.ObservationRepository;
import com.mfptps.appdgessddi.service.ObservationsService;
import com.mfptps.appdgessddi.service.dto.ObservationsDTO;
import com.mfptps.appdgessddi.service.mapper.ObservationsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ObservationsServiceImpl implements ObservationsService {

    private final ObservationRepository observationRepository ;
    private final ObservationsMapper observationsMapper ;

    public ObservationsServiceImpl(ObservationRepository observationRepository, ObservationsMapper observationsMapper) {
        this.observationRepository = observationRepository;
        this.observationsMapper = observationsMapper;
    }

    @Override
    public Observations create(ObservationsDTO observationsDTO) {
        Observations observations=observationsMapper.toEntity(observationsDTO);
        return observationRepository.save(observations);
    }

    @Override
    public Observations update(Observations observations) {
        return observationRepository.save(observations);
    }

    @Override
    public Optional<Observations> get(Long id) {
        return observationRepository.findById(id);
    }

    @Override
    public Page<Observations> findAll(Pageable pageable) {
        return observationRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
   observationRepository.deleteById(id);
    }
}
