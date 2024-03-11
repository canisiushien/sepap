package com.mfptps.appdgessddi.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.entities.IndicateurObjectif;
import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.service.IndicateurObjectifService;
import com.mfptps.appdgessddi.service.dto.IndicateurObjectifDTO;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;

@RestController
@RequestMapping(path = "/api/indicateur/objectif")
public class IndicateurObjectifController {

	private final Logger log = LoggerFactory.getLogger(IndicateurObjectifController.class);

    private static final String ENTITY_NAME = "indicateurs_objectif";

    @Value("${application.name}")
    private String applicationName;
   
    private final IndicateurObjectifService indicateurObjectifService;

	public IndicateurObjectifController(IndicateurObjectifService indicateurObjectifService) {
	
		this.indicateurObjectifService = indicateurObjectifService;
	}
    
	@PostMapping 
    public ResponseEntity<IndicateurObjectif> create(@Valid @RequestBody IndicateurObjectifDTO indicateurObjectifDTO) throws URISyntaxException {
        
       IndicateurObjectif indicateurObjectif = indicateurObjectifService.create(indicateurObjectifDTO);
        log.debug("Création de l'indicateur : {}",indicateurObjectif);
        return ResponseEntity.created(new URI("/api/indicateur/objectif" + indicateurObjectif.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, indicateurObjectif.getId().toString()))
                             .body(indicateurObjectif);    
    }
	
	@PutMapping
    public ResponseEntity<IndicateurObjectif> updateIndic(@Valid @RequestBody IndicateurObjectif indicateur) throws URISyntaxException {
        log.debug("Mis à jour de l'indicateur : {}", indicateur);
        if (indicateur.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        IndicateurObjectif resultO = indicateurObjectifService.update(indicateur);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, indicateur.getId().toString()))
                .body(resultO);
    }
	
	 @GetMapping(path = "/{id}")
	    public ResponseEntity<IndicateurObjectif> getIndicateurById(@PathVariable(name = "id") Long id) {
	        log.debug("Consultation d un Indicateur : {}", id);
	        Optional<IndicateurObjectif> indcateurFound = indicateurObjectifService.get(id);
	        return ResponseUtil.wrapOrNotFound(indcateurFound);
	    }
	
	 @GetMapping
	    public ResponseEntity<List<IndicateurObjectif>> findAll(Pageable pageable) {
	        Page<IndicateurObjectif> indicateur = indicateurObjectifService.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), indicateur);
	        return ResponseEntity.ok().headers(headers).body(indicateur.getContent());
	    }
	 
	 @DeleteMapping(path = "/{id}")
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        log.debug("Suppression d un indicateur : {}", id);
	        indicateurObjectifService.delete(id);
	        return ResponseEntity
	                .noContent()
	                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
	                .build();
	    }

}
