package com.mfptps.appdgessddi.web;


import com.mfptps.appdgessddi.entities.Performer;
import com.mfptps.appdgessddi.service.PerformerService;
import com.mfptps.appdgessddi.service.dto.PerformanceDTO;
import com.mfptps.appdgessddi.service.dto.PerformerDTO;
import com.mfptps.appdgessddi.service.dto.RequeteDTO;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/api/performers")
public class PerformerController {

    private static final String ENTITY_NAME = "performer";

    @Value("${application.name}")
    private String applicationName;

    private final PerformerService performerService;

    public PerformerController(PerformerService performerService) {
        this.performerService = performerService;
    }

    @PostMapping
    public ResponseEntity<Performer> createPerformer(@Valid @RequestBody PerformerDTO performerDto) throws URISyntaxException {
        Performer performerSaved = performerService.create(performerDto);
        log.debug("Création d'un performer : {}", performerDto);
        return ResponseEntity.created(new URI("/api/performers/" + performerSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, performerSaved.getId().toString()))
                .body(performerSaved);
    }
    
    @PostMapping(path = "/calculer")
    public ResponseEntity<PerformanceDTO> calculer(@Valid @RequestBody RequeteDTO requete) throws URISyntaxException {
        PerformanceDTO performance = performerService.calculatePerformance(requete.getMinisterId(), requete.getStructureId(), requete.getExerciceId(), requete.getUserId());
        return ResponseEntity.created(new URI("/api/performers/calculer" + performance.getEfficacite()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, performance.getAppreciation()))
                .body(performance);
    }


    @PutMapping
    public ResponseEntity<Performer> updatePerformer(@Valid @RequestBody Performer performer) throws URISyntaxException {
        log.debug("Mis à jour d un performer : {}", performer);
        if (performer.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Performer result = performerService.update(performer);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, performer.getId().toString()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Performer> getPerformerById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation d un performer : {}", id);
        Optional<Performer> performerFound = performerService.get(id);
        return ResponseUtil.wrapOrNotFound(performerFound);
    }
    
    @GetMapping
    public ResponseEntity<List<Performer>> findAlPerfprmers(Pageable pageable) {
        Page<Performer> performers = performerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), performers);
        return ResponseEntity.ok().headers(headers).body(performers.getContent());
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d un performer : {}", id);
        performerService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
