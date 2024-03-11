/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.service.dto.ProjetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface ProjetMapper {

    /**
     * Here we have the same fields name in both Projet and ProjetDTO. So,
     * values of target referency ProjetDTO fields
     *
     * @param projet
     * @return
     */
    @Mappings({
        @Mapping(target = "libelle", source = "libelle"),
        @Mapping(target = "description", source = "description"),
        @Mapping(target = "statut", source = "statut"),
        @Mapping(target = "debut", source = "debut"),
        @Mapping(target = "fin", source = "fin"),
        @Mapping(target = "programme", source = "programme")})
    ProjetDTO toDto(Projet projet);

    /**
     * Convert a DTO to Projet entity
     *
     * @param projetDTO
     * @return
     */
    @Mappings({
        @Mapping(target = "libelle", source = "libelle"),
        @Mapping(target = "description", source = "description"),
        @Mapping(target = "statut", source = "statut"),
        @Mapping(target = "debut", source = "debut"),
        @Mapping(target = "fin", source = "fin"),
        @Mapping(target = "programme", source = "programme")})
    Projet toEntity(ProjetDTO projetDTO);
}
