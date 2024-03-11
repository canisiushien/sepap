/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Periodicite;
import com.mfptps.appdgessddi.service.PeriodiciteService;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.PaginationUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(path = "/api/periodicites")
public class PeriodiciteController {

    private static final String ENTITY_NAME = "periodicite";

    @Value("${application.name}")
    private String applicationName;

    private final PeriodiciteService periodiciteService;

    public PeriodiciteController(PeriodiciteService periodiciteService) {
        this.periodiciteService = periodiciteService;
    }

    /**
     * Access granted to RESP_DGESS, ADMIN
     *
     * @param periodicite :
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Periodicite> createPeriodicite(@Valid @RequestBody Periodicite periodicite) throws URISyntaxException {
        Periodicite dataSaved = periodiciteService.create(periodicite);
        log.debug("Création d'une Periodicite : {}", periodicite);
        return ResponseEntity.created(new URI("/api/periodicites/" + dataSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, dataSaved.getId().toString()))
                .body(dataSaved);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Periodicite>> findAllPeriodicites(Pageable pageable) {
        Page<Periodicite> liste = periodiciteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), liste);
        return ResponseEntity.ok().headers(headers).body(liste.getContent());
    }

    /**
     *
     * @return
     */
    @GetMapping(path = "/actif")
    public ResponseEntity<Periodicite> getPeriodiciteActif() {
        log.debug("Consultation de la Periodicite active : {}");
        Optional<Periodicite> periodicite = periodiciteService.getActif();
        return ResponseUtil.wrapOrNotFound(periodicite);
    }
}
