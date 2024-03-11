package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Action;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.service.dto.ActionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ActionMapper {


    ActionDTO toDto(Action action);

    Action toEntity(ActionDTO actionDTO);

}
