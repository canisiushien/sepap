package com.mfptps.appdgessddi.service.mapper;



import com.mfptps.appdgessddi.entities.Performer;
import com.mfptps.appdgessddi.service.dto.PerformerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PerformerMapper {


    @Mappings({
            @Mapping(target = "libelle", source = "libelle"),
            @Mapping(target = "moyenneEfficacite", source = "moyenneEfficacite"),
            @Mapping(target = "moyenneEfficience", source = "moyenneEfficience"),
            @Mapping(target = "moyenneImpact", source = "moyenneImpact"),
            @Mapping(target = "moyenneGouvernance", source = "moyenneGouvernance"),
            @Mapping(target = "moyennePGS", source = "moyennePGS"),
            @Mapping(target = "PGM", source = "PGM"),
            @Mapping(target = "performance", source = "performance"),

    })
    PerformerDTO toDto(Performer performer);
    @Mappings({
            @Mapping(target = "libelle", source = "libelle"),
            @Mapping(target = "moyenneEfficacite", source = "moyenneEfficacite"),
            @Mapping(target = "moyenneEfficience", source = "moyenneEfficience"),
            @Mapping(target = "moyenneImpact", source = "moyenneImpact"),
            @Mapping(target = "moyenneGouvernance", source = "moyenneGouvernance"),
            @Mapping(target = "moyennePGS", source = "moyennePGS"),
            @Mapping(target = "PGM", source = "PGM"),
            @Mapping(target = "performance", source = "performance"),


    })
    Performer toEntity(PerformerDTO performerDTO);
}
