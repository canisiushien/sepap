package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Parametre;
import com.mfptps.appdgessddi.service.dto.ParametreDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParametreMapper {

    @Mapping(source = "echeance", target = "echeance")
    @Mapping(source = "verrouille", target = "verrouille")
//    @Mapping(source = "dateDebutExercice", target = "dateDebutExercice")
//    @Mapping(source = "dateFinExercice", target = "dateFinExercice")
    @Mapping(source = "dateDebutSaisit", target = "dateDebutSaisit")
    @Mapping(source = "dateFinSaisit", target = "dateFinSaisit")
    Parametre toEntity(ParametreDTO parametreDTO);

    @Mapping(source = "echeance", target = "echeance")
    @Mapping(source = "verrouille", target = "verrouille")
//    @Mapping(source = "dateDebutExercice", target = "dateDebutExercice")
//    @Mapping(source = "dateFinExercice", target = "dateFinExercice")
    @Mapping(source = "dateDebutSaisit", target = "dateDebutSaisit")
    @Mapping(source = "dateFinSaisit", target = "dateFinSaisit")
    ParametreDTO toDto(Parametre parametre);
}
