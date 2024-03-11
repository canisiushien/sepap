/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Programme;
import com.mfptps.appdgessddi.service.dto.ProgrammeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ProgrammeMapper {

    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    @Mapping(source = "statut", target = "statut")
    @Mapping(source = "debut", target = "debut")
    @Mapping(source = "fin", target = "fin")
    Programme toEntity(ProgrammeDTO programmeDTO);

    @Mapping(source = "code", target = "code")
    @Mapping(source = "libelle", target = "libelle")
    @Mapping(source = "statut", target = "statut")
    @Mapping(source = "debut", target = "debut")
    @Mapping(source = "fin", target = "fin")
    ProgrammeDTO toDto(Programme programme);

}
