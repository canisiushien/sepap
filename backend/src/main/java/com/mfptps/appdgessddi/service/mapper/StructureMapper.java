package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Structure;
import com.mfptps.appdgessddi.service.dto.StructureDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StructureMapper {

    StructureDTO toDto(Structure structure);

    Structure toEntity(StructureDTO structureDTO);

    default Structure fromId(Long id) {
        if (id == null) {
            return null;
        }
        Structure structure = new Structure();
        structure.setId(id);
        return structure;
    }

}
