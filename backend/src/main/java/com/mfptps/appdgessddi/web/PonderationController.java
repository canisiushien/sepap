package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Ponderation;
import com.mfptps.appdgessddi.service.PonderationService;
import com.mfptps.appdgessddi.service.dto.PonderationDTO;
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
@RequestMapping(path = "/api/ponderations")
public class PonderationController {
    private static final String ENTITY_NAME = "ponderation";

    @Value("${application.name}")
    private String applicationName;

    private final PonderationService ponderationService ;

    public PonderationController(PonderationService ponderationService) {
        this.ponderationService = ponderationService;
    }

    @PostMapping
    public ResponseEntity<Ponderation> createPonderation(@Valid @RequestBody PonderationDTO ponderationDTO) throws URISyntaxException {
        Ponderation ponderation = ponderationService.create(ponderationDTO);
        log.debug("Création d'une ponderation : {}", ponderationDTO);
        return ResponseEntity.created(new URI("/api/pondrations/" + ponderation.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, ponderation.getId().toString()))
                .body(ponderation);
    }

    @PutMapping
    public ResponseEntity<Ponderation> updatePonderation(@Valid @RequestBody Ponderation ponderation) throws URISyntaxException {
        log.debug("Mis à jour du Ponderation : {}", ponderation);
        if (ponderation.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Ponderation result = ponderationService.update(ponderation);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, ponderation.getId().toString()))
                .body(result);
    }
    @GetMapping(path = "/{id}")
    public ResponseEntity<Ponderation> getPonderationById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation d'une ponderation : {}", id);
        Optional<Ponderation> ponderationFound = ponderationService.get(id);
        return ResponseUtil.wrapOrNotFound(ponderationFound);
    }
    @GetMapping
    public ResponseEntity<List<Ponderation>> findAllPonderations(Pageable pageable) {
        Page<Ponderation> ponderations = ponderationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), ponderations);
        return ResponseEntity.ok().headers(headers).body(ponderations.getContent());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d'une ponderation: {}", id);
        ponderationService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
