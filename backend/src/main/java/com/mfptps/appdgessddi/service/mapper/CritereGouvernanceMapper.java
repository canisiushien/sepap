package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.CritereGouvernance;
import com.mfptps.appdgessddi.service.dto.CritereGouvernanceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CritereGouvernanceMapper {
    CritereGouvernanceDTO toDto(CritereGouvernance critereGouvernance);
    CritereGouvernance toEntity(CritereGouvernanceDTO critereGouvernanceDTO);

}
