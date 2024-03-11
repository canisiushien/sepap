package com.mfptps.appdgessddi.service.impl;

import com.mfptps.appdgessddi.entities.EvaluationGouvernance;
import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.repositories.EvaluationGouvernanceRepository;
import com.mfptps.appdgessddi.repositories.MinistereStructureRepository;
import com.mfptps.appdgessddi.security.SecurityUtils;
import com.mfptps.appdgessddi.service.CustomException;
import com.mfptps.appdgessddi.service.EvaluationGouvernanaceService;
import com.mfptps.appdgessddi.service.dto.CritereDTO;
import com.mfptps.appdgessddi.service.dto.EvaluationGouvernanceDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class EvaluationGouvernanaceServiceImpl implements EvaluationGouvernanaceService {

    private final EvaluationGouvernanceRepository evaluationGouvernanceRepository;
    private final MinistereStructureRepository ministereStructureRepository;

    public EvaluationGouvernanaceServiceImpl(EvaluationGouvernanceRepository evaluationGouvernanceRepository,
            MinistereStructureRepository ministereStructureRepository) {
        this.evaluationGouvernanceRepository = evaluationGouvernanceRepository;
        this.ministereStructureRepository = ministereStructureRepository;
    }

    @Override
    public EvaluationGouvernance create(EvaluationGouvernanceDTO evaluationGouvernanceDTO) {
        String matricule = SecurityUtils.getCurrentUserMatricule().get();
        Structure structure = ministereStructureRepository.findByAgentMatricule(matricule).orElseThrow(() -> new CustomException("Opération non autorisée car vous ne relevez d'aucune structure."));
        if (evaluationGouvernanceDTO.getStructure().getId() != structure.getId()) {
            throw new CustomException("Opération non autorisée car vous ne relevez pas de " + evaluationGouvernanceDTO.getStructure().getSigle());
        }

        List< EvaluationGouvernance> evaluationGouvernances = new ArrayList<>();

        for (CritereDTO critere : evaluationGouvernanceDTO.getCritereGouvernances()) {
            EvaluationGouvernance eg = new EvaluationGouvernance();

            eg.setValeurReference(!critere.getCritereGouvernance().isMode() ? critere.getValeurReference() : 1D);
            eg.setCritereGouvernance(critere.getCritereGouvernance());
            eg.setNonapplicable(critere.isNonapplicable());
            eg.setExercice(evaluationGouvernanceDTO.getExercice());
            eg.setStructure(evaluationGouvernanceDTO.getStructure());

            evaluationGouvernances.add(eg);
        }

        evaluationGouvernances = evaluationGouvernanceRepository.saveAll(evaluationGouvernances);

        return evaluationGouvernances.get(0);
    }

    @Override
    public EvaluationGouvernance update(EvaluationGouvernance evaluationGouvernance) {
        return evaluationGouvernanceRepository.save(evaluationGouvernance);
    }

    @Override
    public Optional<EvaluationGouvernance> get(Long id) {
        return evaluationGouvernanceRepository.findById(id);
    }

    @Override
    public Page<EvaluationGouvernance> get(Pageable pageable) {
        return evaluationGouvernanceRepository.findAll(pageable);
    }

    @Override
    public List<EvaluationGouvernance> findStructureEvaluation(Long structureId, Long exerciceId) {
        return evaluationGouvernanceRepository.findStructureEvaluation(structureId, exerciceId);
    }
}
