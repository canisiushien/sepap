package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Exercice;
import com.mfptps.appdgessddi.service.dto.ExerciceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExerciceMapper {

    ExerciceDTO toDto(Exercice exercice);

    Exercice toEntity(ExerciceDTO exerciceDTO);
}
