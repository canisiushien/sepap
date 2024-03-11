package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.service.dto.PonderationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PonderationMapper {

    @Mappings({
            @Mapping(target = "efficacite", source = "efficacite"),
            @Mapping(target = "efficience", source = "efficience"),
            @Mapping(target = "gouvernance", source = "gouvernance"),
            @Mapping(target = "impact", source = "impact"),
            @Mapping(target = "actif", source = "actif"),
            @Mapping(target = "performance", source = "performance")})
    PonderationDTO toDto(Ponderation ponderation);

    @Mappings({
            @Mapping(target = "efficacite", source = "efficacite"),
            @Mapping(target = "efficience", source = "efficience"),
            @Mapping(target = "gouvernance", source = "gouvernance"),
            @Mapping(target = "impact", source = "impact"),
            @Mapping(target = "actif", source = "actif"),
            @Mapping(target = "performance", source = "performance")})
    Ponderation toEntity(PonderationDTO ponderationDTO);

}
