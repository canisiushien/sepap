/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.SourceFinancement;
import com.mfptps.appdgessddi.service.SourceFinancementService;
import com.mfptps.appdgessddi.utils.*;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/source-financements")
public class SourceFinancementController {

    private static final String ENTITY_NAME = "source_financement";

    @Value("${application.name}")
    private String applicationName;

    private final SourceFinancementService sourceFinancementService;

    public SourceFinancementController(SourceFinancementService sourceFinancementService) {
        this.sourceFinancementService = sourceFinancementService;
    }

    /**
     * Access granted to DAF, RESP_DGESS, (ADMIN)
     *
     * @param sourceFinancement
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DAF + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<SourceFinancement> createSourceFinancement(@Valid @RequestBody SourceFinancement sourceFinancement) throws URISyntaxException {
        AppUtil.checkDebutBeforeFin(sourceFinancement.getDebut(), sourceFinancement.getFin());
        SourceFinancement entitySaved = sourceFinancementService.create(sourceFinancement);
        log.debug("Création d'une SourceFinancement : {}", sourceFinancement);
        return ResponseEntity.created(new URI("/api/source-financements" + entitySaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, entitySaved.getId().toString()))
                .body(entitySaved);
    }

    /**
     * Access granted to DAF, RESP_DGESS, (ADMIN)
     *
     * @param sourceFinancement
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DAF + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<SourceFinancement> updateSourceFinancement(@Valid @RequestBody SourceFinancement sourceFinancement) throws URISyntaxException {
        log.debug("Mis à jour d'une SourceFinancement : {}", sourceFinancement);
        if (sourceFinancement.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        AppUtil.checkDebutBeforeFin(sourceFinancement.getDebut(), sourceFinancement.getFin());
        SourceFinancement result = sourceFinancementService.update(sourceFinancement);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, sourceFinancement.getId().toString()))
                .body(result);
    }

    /**
     *
     * @param libelle
     * @return
     */
    @GetMapping(path = "/{libelle}")
    public ResponseEntity<List<SourceFinancement>> findSourceByLibelle(@PathVariable(name = "libelle", required = true) String libelle) {
        List<SourceFinancement> sources = sourceFinancementService.find(libelle);
        return ResponseEntity.ok().body(sources);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<List<SourceFinancement>> findAllSources(Pageable pageable) {
        Page<SourceFinancement> sources = sourceFinancementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), sources);
        return ResponseEntity.ok().headers(headers).body(sources.getContent());
    }
}
