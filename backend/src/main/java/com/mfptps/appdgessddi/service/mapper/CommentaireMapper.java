/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Commentaire;
import com.mfptps.appdgessddi.service.dto.CommentaireDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface CommentaireMapper {

    @Mappings({
        @Mapping(target = "programmationId", source = "programmation.id"),
        @Mapping(target = "contenu", source = "contenu")})
    CommentaireDTO toDTO(Commentaire commentaire);

    @Mappings({
        @Mapping(target = "programmation.id", source = "programmationId"),
        @Mapping(target = "contenu", source = "contenu")})
    Commentaire toEntity(CommentaireDTO commentaireDTO);

}
