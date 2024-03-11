package com.mfptps.appdgessddi.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.mfptps.appdgessddi.entities.IndicateurObjectif;
import com.mfptps.appdgessddi.service.dto.IndicateurObjectifDTO;



@Mapper(componentModel = "spring")

public interface IndicateurObjectifMapper {

	
	    @Mapping(source = "description", target = "description")
		@Mapping(source = "libelle", target = "libelle")
		@Mapping(source = "typeIndicateur", target = "typeIndicateur")
	    @Mapping(source = "valeur", target = "valeur")
	//   @Mapping(source = "objectif", target = "objectif.id")
		
	    IndicateurObjectif toEntity(IndicateurObjectifDTO indicateurObjectifDTO);

	    @Mapping(source = "description", target = "description")  
		@Mapping(source = "libelle", target = "libelle")
		@Mapping(source = "typeIndicateur", target = "typeIndicateur")
	    @Mapping(source = "valeur", target = "valeur")
	   // @Mapping(source = "objectif.id", target = "objectif")
	    
	    IndicateurObjectifDTO toDto(IndicateurObjectif indicateurObjectif);
}
