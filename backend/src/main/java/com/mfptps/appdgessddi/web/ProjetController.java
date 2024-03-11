/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Projet;
import com.mfptps.appdgessddi.service.ProjetService;
import com.mfptps.appdgessddi.service.dto.ProjetDTO;
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
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(path = "/api/projets")
public class ProjetController {

    private static final String ENTITY_NAME = "projet";

    @Value("${application.name}")
    private String applicationName;

    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param projetDto
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Projet> createProjet(@Valid @RequestBody ProjetDTO projetDto) throws URISyntaxException {
        AppUtil.checkDebutBeforeFin(projetDto.getDebut(), projetDto.getFin());
        Projet projetSaved = projetService.create(projetDto);
        log.debug("Création du Projet : {}", projetDto);
        return ResponseEntity.created(new URI("/api/projets/" + projetSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, projetSaved.getId().toString()))
                .body(projetSaved);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param projet
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Projet> updateProjet(@Valid @RequestBody Projet projet) throws URISyntaxException {
        log.debug("Mis à jour du Projet : {}", projet);
        if (projet.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        AppUtil.checkDebutBeforeFin(projet.getDebut(), projet.getFin());
        Projet result = projetService.update(projet);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projet.getId().toString()))
                .body(result);
    }

    /**
     *
     * @param libelle : Projet libelle to be found
     * @return
     */
    @GetMapping(path = "/{libelle}")
    public ResponseEntity<Projet> getProjetByLibelle(@PathVariable(name = "libelle") String libelle) {
        log.debug("Consultation du Projet : {}", libelle);
        Optional<Projet> projetFound = projetService.get(libelle);
        return ResponseUtil.wrapOrNotFound(projetFound);
    }

    /**
     *
     * @param id : Projet id to be found
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Projet> getProjetById(@PathVariable(name = "id") Long id) {
        log.debug("Consultation du Projet : {}", id);
        Optional<Projet> projetFound = projetService.get(id);
        return ResponseUtil.wrapOrNotFound(projetFound);
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Projet>> findAllProjets(Pageable pageable) {
        Page<Projet> projets = projetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), projets);
        return ResponseEntity.ok().headers(headers).body(projets.getContent());
    }

    /**
     * Access granted to ADMIN
     *
     * @param id : Projet id to be deleted
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression du Projet : {}", id);
        projetService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
