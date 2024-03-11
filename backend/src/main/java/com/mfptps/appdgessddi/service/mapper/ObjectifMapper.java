package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.service.dto.ObjectifDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ObjectifMapper {

    @Mappings({
        @Mapping(target = "code", source = "code"),
        @Mapping(target = "libelle", source = "libelle"),
        @Mapping(target = "type", source = "type"),
        @Mapping(target = "parent", source = "parent"),
        @Mapping(target = "action", source = "action"),
        @Mapping(target = "programme", source = "programme")})
    Objectif toEntity(ObjectifDTO objectifDTO);

    @Mappings({
        @Mapping(target = "code", source = "code"),
        @Mapping(target = "libelle", source = "libelle"),
        @Mapping(target = "type", source = "type"),
        @Mapping(target = "parent", source = "parent"),
        @Mapping(target = "action", source = "action"),
        @Mapping(target = "programme", source = "programme")})
    ObjectifDTO toDto(Objectif objectif);

}
