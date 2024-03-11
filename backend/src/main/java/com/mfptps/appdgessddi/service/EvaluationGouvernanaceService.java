package com.mfptps.appdgessddi.service;

import com.mfptps.appdgessddi.entities.EvaluationGouvernance;
import com.mfptps.appdgessddi.service.dto.EvaluationGouvernanceDTO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EvaluationGouvernanaceService {

    EvaluationGouvernance create(EvaluationGouvernanceDTO evaluationGouvernanceDTO);
    EvaluationGouvernance update(EvaluationGouvernance evaluationGouvernance);
    Optional<EvaluationGouvernance> get (Long id);
    Page<EvaluationGouvernance> get(Pageable pageable);
    List<EvaluationGouvernance> findStructureEvaluation(Long structureId, Long exerciceId);
}
