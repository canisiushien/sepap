/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Programmation;
import com.mfptps.appdgessddi.service.dto.ProgrammationDTO;
import com.mfptps.appdgessddi.service.dto.ProgrammationForEvaluationDTO;
import org.mapstruct.Mapper;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ProgrammationMapper {

//    @Mappings({
//        @Mapping(target = "structure", source = "structure"),
//        @Mapping(target = "projet", source = "projet"),
//        @Mapping(target = "sourceFinancement", source = "sourceFinancement"),
//        @Mapping(target = "objectif", source = "objectif"),
//        @Mapping(target = "activite", source = "activite"),
//        @Mapping(target = "taches", source = "taches")})
    ProgrammationDTO toDTO(Programmation programmation);

//    @Mappings({
//        @Mapping(target = "coutPrevisionnel", source = "coutPrevisionnel"),
//        @Mapping(target = "coutReel", source = "coutReel"),
//        @Mapping(target = "cible", source = "cible"),
//        @Mapping(target = "observations", source = "observations"),
//        @Mapping(target = "singleton", source = "singleton"),
//        @Mapping(target = "structure", source = "structure"),
//        @Mapping(target = "projet", source = "projet"),
//        @Mapping(target = "sourceFinancement", source = "sourceFinancement"),
//        @Mapping(target = "objectif", source = "objectif"),
//        @Mapping(target = "activite", source = "activite"),
//        @Mapping(target = "taches", source = "taches")})don't forgot code field
    Programmation toEntity(ProgrammationDTO programmationDTO);

    ProgrammationForEvaluationDTO toEvaluationDTO(Programmation programmation);

}
