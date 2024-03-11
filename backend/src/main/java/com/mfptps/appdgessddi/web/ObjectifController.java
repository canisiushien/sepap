package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Objectif;
import com.mfptps.appdgessddi.service.ObjectifService;
import com.mfptps.appdgessddi.service.dto.ObjectifDTO;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Slf4j
@RestController
@RequestMapping(path = "/api/objectifs")
public class ObjectifController {

    private static final String ENTITY_NAME = "objectif";

    @Value("${application.name}")
    private String applicationName;
    private final ObjectifService objectifService;

    public ObjectifController(ObjectifService objectifService) {
        this.objectifService = objectifService;
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param objectifDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Objectif> createObjectif(@Valid @RequestBody ObjectifDTO objectifDTO) throws URISyntaxException {
        Objectif objectif = objectifService.create(objectifDTO);
        log.debug("Création d un objectif : {}", objectifDTO);
        return ResponseEntity.created(new URI("/api/objectifs/" + objectif.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, objectif.getId().toString()))
                .body(objectif);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param objectif
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Objectif> updateObjectif(@Valid @RequestBody Objectif objectif) throws URISyntaxException {
        log.debug("Mis à jour d un objectif : {}", objectif);
        if (objectif.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Objectif resultO = objectifService.update(objectif);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, objectif.getId().toString()))
                .body(resultO);
    }

    @GetMapping(path = "/libelle/{libelle}")
    public ResponseEntity<Objectif> getObjectifByLibelle(@PathVariable(name = "libelle") String libelle) {
        log.debug("Consultation d un objectif : {}", libelle);
        Optional<Objectif> objectifFound = objectifService.get(libelle);
        return ResponseUtil.wrapOrNotFound(objectifFound);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Objectif> getObjectifById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation d un objectif : {}", id);
        Optional<Objectif> objectif = objectifService.get(id);
        return ResponseUtil.wrapOrNotFound(objectif);
    }

    /**
     *
     * @param type : field type of objectif
     * @param pageable
     * @return
     */
    @GetMapping(path = "/search/type/{type}")
    public ResponseEntity<List<Objectif>> findObjectifsByType(@PathVariable(name = "type", required = true) String type, Pageable pageable) {
        Page<Objectif> objectifs = objectifService.findByType(type, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), objectifs);
        return ResponseEntity.ok().headers(headers).body(objectifs.getContent());
    }

    @GetMapping
    public ResponseEntity<List<Objectif>> findAllObjectifs(Pageable pageable) {
        Page<Objectif> objectifs = objectifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), objectifs);
        return ResponseEntity.ok().headers(headers).body(objectifs.getContent());
    }

    /**
     * Access granted to ADMIN
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression d un objectif : {}", id);
        objectifService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
