package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.entities.Performance;
import com.mfptps.appdgessddi.enums.ExerciceStatus;
import com.mfptps.appdgessddi.repositories.ExerciceRepository;
import com.mfptps.appdgessddi.repositories.PerformanceRepository;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.PerformanceService;
import com.mfptps.appdgessddi.service.dto.PerformanceDTO;
import com.mfptps.appdgessddi.service.mapper.PerformanceMapper;
import java.util.Arrays;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final ExerciceRepository exerciceRepository;
    private final PerformanceMapper performanceMapper;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository,
            ExerciceRepository exerciceRepository,
            PerformanceMapper performanceMapper) {
        this.performanceRepository = performanceRepository;
        this.exerciceRepository = exerciceRepository;
        this.performanceMapper = performanceMapper;
    }

    @Override
    public Performance create(PerformanceDTO performanceDTO) {
        Performance performance = performanceMapper.toEntity(performanceDTO);
        return performanceRepository.save(performance);
    }

    @Override
    public Performance update(Performance performance) {
        return performanceRepository.save(performance);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Performance> get(Long id) {
        return performanceRepository.findById(id);
    }

    @Override
    public Page<Performance> getByStructure(long structureId, long exerciceId, Pageable pageable) {
        Page<Performance> pageConstruct = null;

        Optional<Performance> perf = performanceRepository.findCurrentStructurePerformance(structureId, exerciceId);

        if (perf.isPresent()) {
            pageConstruct = new PageImpl<Performance>(Arrays.asList(perf.get()), pageable, Arrays.asList(perf.get()).size());
        } else {
            throw new CustomException("Aucun élément (structure - performance - exercice) trouvé !");
        }

        return pageConstruct;
    }

    @Override
    public Page<Performance> getByStructureAndExerciceENCOURS(long structureId) {
        Page<Performance> pageConstruct = null;
        Exercice exercice = exerciceRepository.findByStatut(ExerciceStatus.EN_COURS).orElseThrow(() -> new CustomException("Aucun exercice en cours"));

        Optional<Performance> perf = performanceRepository.findCurrentStructurePerformance(structureId, exercice.getId());

        if (perf.isPresent()) {
            pageConstruct = new PageImpl<Performance>(Arrays.asList(perf.get()));//SANS PAGEABLE
        } else {
            throw new CustomException("Aucun élément (structure - performance) trouvé !");
        }

        return pageConstruct;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Performance> findAll(Pageable pageable) {
        return performanceRepository.findAll(pageable);
    }

    @Override
    public Page<Performance> findAllByMinistere(long ministereId, long exerciceId, Pageable pageable) {
        return performanceRepository.findAllByMinistere(ministereId, exerciceId, pageable);
    }

    @Override
    public Page<Performance> findAllByMinistereAndExerciceENCOURS(long ministereId, Pageable pageable) {
        Optional<Exercice> exercice = exerciceRepository.findByStatut(ExerciceStatus.EN_COURS);

        if (exercice.isPresent()) {
            return performanceRepository.findAllByMinistere(ministereId, exercice.get().getId(), pageable);
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        performanceRepository.deleteById(id);
    }

}
