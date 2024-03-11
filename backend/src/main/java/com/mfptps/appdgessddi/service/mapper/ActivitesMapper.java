package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Activites;
import com.mfptps.appdgessddi.service.dto.ActivitesDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface ActivitesMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "typeActivites", target = "typeActivites")
    @Mapping(source = "status", target = "status")
    //@Mapping(source = "typeActivitesId", target = "typeActivites.id")

    Activites toEntity(ActivitesDTO activitesDTO);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "typeActivites", target = "typeActivites")
    //@Mapping(source = "typeActivites.id", target = "typeActivitesId")

    ActivitesDTO toDto(Activites activites);
}
