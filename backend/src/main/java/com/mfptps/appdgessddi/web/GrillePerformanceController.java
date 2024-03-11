package com.mfptps.appdgessddi.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.entities.GrillePerformance;
import com.mfptps.appdgessddi.service.GrillePerformanceService;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;

import org.slf4j.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "/api")
public class GrillePerformanceController {

    private final Logger log = LoggerFactory.getLogger(GrillePerformanceController.class);

    private static final String ENTITY_NAME = "grillePerformance";

    @Value("${application.name}")
    private String applicationName;
    
    private final GrillePerformanceService grillePerformanceService;

    public GrillePerformanceController(GrillePerformanceService grillePerformanceService) {
        this.grillePerformanceService = grillePerformanceService;
    }

    @PostMapping(path = "/grille-performances")
    public ResponseEntity<GrillePerformance> create(@Valid @RequestBody GrillePerformance grillePerformance) throws URISyntaxException {
        
        GrillePerformance min = grillePerformanceService.create(grillePerformance);
        log.debug("Création du GrillePerformance : {}", grillePerformance);
        return ResponseEntity.created(new URI("/api/grille-performances/" + min.getId()))
                             .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, min.getId().toString()))
                             .body(min);
    }

    @PutMapping(path = "/grille-performances")
    public ResponseEntity<GrillePerformance> updateGrillePerformance(@Valid @RequestBody GrillePerformance grillePerformance) throws URISyntaxException {
        log.debug("Mis à jour du GrillePerformance : {}", grillePerformance);
        if (grillePerformance.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        GrillePerformance result = grillePerformanceService.update(grillePerformance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, grillePerformance.getId().toString()))
            .body(result);
    }

    @GetMapping(path = "/grille-performances/{id}")
    public ResponseEntity<GrillePerformance> getGrillePerformance(@PathVariable(name = "id") Long id) {
        log.debug("Consultation de GrillePerformance : {}", id);
        Optional<GrillePerformance> grillePerformance = grillePerformanceService.get(id);
        return ResponseUtil.wrapOrNotFound(grillePerformance);
    }

    @GetMapping(path = "/grille-performances")
    public ResponseEntity<List<GrillePerformance>> findAllGrillePerformances(Pageable pageable) {
        Page<GrillePerformance> minsiteres = grillePerformanceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), minsiteres);
        return ResponseEntity.ok().headers(headers).body(minsiteres.getContent());
    }

    @DeleteMapping(path = "/grille-performances/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression du GrillePerformance : {}", id);
        grillePerformanceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
    
}
