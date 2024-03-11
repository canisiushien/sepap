package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Observations;
import com.mfptps.appdgessddi.service.dto.ObservationsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ObservationsMapper {

    @Mappings({
            @Mapping(target = "libelle", source = "libelle"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "performer", source = "performer")
            })
    ObservationsDTO toDto(Observations observations);

    @Mappings({
            @Mapping(target = "libelle", source = "libelle"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "performer", source = "performer")
    })
    Observations toEntity(ObservationsDTO observationsDTO);

}
