/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Periode;
import com.mfptps.appdgessddi.service.PeriodeService;
import com.mfptps.appdgessddi.utils.AppUtil;
import com.mfptps.appdgessddi.utils.HeaderUtil;
import com.mfptps.appdgessddi.utils.ResponseUtil;
import com.mfptps.appdgessddi.web.exceptions.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Canisius <canisiushien@gmail.com>
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/periodes")
public class PeriodeController {

    private static final String ENTITY_NAME = "periode";

    @Value("${application.name}")
    private String applicationName;

    private final PeriodeService periodeService;

    public PeriodeController(PeriodeService periodeService) {
        this.periodeService = periodeService;
    }

    /**
     * Access granted to ADMIN
     *
     * @param periode
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Periode> createPeriode(@Valid @RequestBody Periode periode) throws URISyntaxException {
        AppUtil.checkDebutBeforeFin(periode.getDebut(), periode.getFin());
        Periode dataSaved = periodeService.create(periode);
        log.debug("Création d'une Periode : {}", periode);
        return ResponseEntity.created(new URI("/api/periodes/" + dataSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, dataSaved.getId().toString()))
                .body(dataSaved);
    }

    /**
     * Access granted to ADMIN
     *
     * @param periode
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Periode> updatePeriode(@Valid @RequestBody Periode periode) throws URISyntaxException {
        AppUtil.checkDebutBeforeFin(periode.getDebut(), periode.getFin());
        if (periode.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }

        Periode dataSaved = periodeService.update(periode);
        log.debug("Modification d'une Periode : {}", periode);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, dataSaved.getId().toString()))
                .body(dataSaved);
    }

    /**
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Periode>> findByPeriodiciteActif() {
        List<Periode> liste = periodeService.findByPeriodiciteActif();
        return ResponseEntity.ok().body(liste);
    }

    /**
     *
     * @param date
     * @return
     */
    @GetMapping(path = "/{date}")
    public ResponseEntity<Periode> getPeriodeByDate(@PathVariable(required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        log.debug("Consultation d'une Periode par date : {}", date);
        Optional<Periode> data = periodeService.findByDateAndPeriodiciteActif(date);
        return ResponseUtil.wrapOrNotFound(data);
    }
}
