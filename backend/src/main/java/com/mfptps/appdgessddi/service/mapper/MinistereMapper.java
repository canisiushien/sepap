package com.mfptps.appdgessddi.service.mapper;

import com.mfptps.appdgessddi.entities.Ministere;
import com.mfptps.appdgessddi.service.dto.MinistereDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface MinistereMapper {

    @Mappings({
        @Mapping(target = "code", source = "ministere.code")
    })
    MinistereDTO toDto(Ministere ministere);

    @Mappings({
        @Mapping(target = "code", source = "ministereDTO.code")
    })
    Ministere toEntity(MinistereDTO ministereDTO);
}
