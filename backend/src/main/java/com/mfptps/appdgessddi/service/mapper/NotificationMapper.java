/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Notification;
import com.mfptps.appdgessddi.service.dto.NotificationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Fatogoma HEBIE <fat19ebie@gmail.com>
 */
@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "objet", target = "objet")
    @Mapping(source = "contenu", target = "contenu")
    Notification toEntity(NotificationDTO notificationDTO);
    
    @Mapping(source = "id", target = "id")
    @Mapping(source = "objet", target = "objet")
    @Mapping(source = "contenu", target = "contenu")
    NotificationDTO toDto(Notification notification);

}