/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Document;
import com.mfptps.appdgessddi.service.dto.DocumentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(target = "tacheId", source = "tache.id")
    DocumentDTO toDto(Document document);

    @Mapping(target = "tache.id", source = "tacheId")
    Document toEntity(DocumentDTO documentDTO);
}
