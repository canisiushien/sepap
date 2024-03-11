package com.mfptps.appdgessddi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.mfptps.appdgessddi.entities.TypeActivites;

import com.mfptps.appdgessddi.service.dto.TypeActiviteDTO;

@Mapper(componentModel = "spring")
public interface TypeActiviteMapper {

    @Mappings({
        @Mapping(target = "libelle", source = "typeActivites.libelle")
    })
    TypeActiviteDTO toDto(TypeActivites typeActivites);

    @Mappings({
        @Mapping(target = "libelle", source = "typeActiviteDTO.libelle")
    })
    TypeActivites toEntity(TypeActiviteDTO typeActiviteDTO);
}
