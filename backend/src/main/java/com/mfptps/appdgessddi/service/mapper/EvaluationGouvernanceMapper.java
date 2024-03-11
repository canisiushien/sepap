package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.EvaluationGouvernance;
import com.mfptps.appdgessddi.service.dto.EvaluationGouvernanceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EvaluationGouvernanceMapper {
    EvaluationGouvernanceDTO toDto(EvaluationGouvernance evaluationGouvernance);
    EvaluationGouvernance toEntity(EvaluationGouvernanceDTO evaluationGouvernanceDTO);
}
