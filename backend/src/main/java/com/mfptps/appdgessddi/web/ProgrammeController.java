/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.entities.Programme;
import com.mfptps.appdgessddi.service.ProgrammeService;
import com.mfptps.appdgessddi.service.dto.ProgrammeDTO;
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
@RequestMapping(path = "/api/programmes")
public class ProgrammeController {

    private static final String ENTITY_NAME = "programme";

    @Value("${application.name}")
    private String applicationName;

    private final ProgrammeService programmeService;

    public ProgrammeController(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param programmeDTO
     * @return
     * @throws URISyntaxException
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Programme> createProgramme(@Valid @RequestBody ProgrammeDTO programmeDTO) throws URISyntaxException {
        Programme programmeSaved = programmeService.create(programmeDTO);
        log.debug("Création du Programme : {}", programmeDTO);
        return ResponseEntity.created(new URI("/api/programmes/" + programmeSaved.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, programmeSaved.getId().toString()))
                .body(programmeSaved);
    }

    /**
     * Access granted to DIR_DGESS, RESP_DGESS, (ADMIN)
     *
     * @param programme : json Programme object
     * @return
     * @throws URISyntaxException
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.DD + "\",\"" + AppUtil.RD + "\", \"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Programme> updateProgramme(@Valid @RequestBody Programme programme) throws URISyntaxException {
        log.debug("Mis à jour du Programme : {}", programme);
        if (programme.getId() == null) {
            throw new BadRequestAlertException("Id invalide", ENTITY_NAME, "idnull");
        }
        Programme result = programmeService.update(programme);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, programme.getId().toString()))
                .body(result);
    }

    /**
     *
     * @param code : code of Programme to be found
     * @return
     */
    @GetMapping(path = "/{code}")
    public ResponseEntity<List<Programme>> getProgrammeByCode(@PathVariable(name = "code", required = true) String code, Pageable pageable) {
        log.debug("Consultation du Programme : {}", code);
        Page<Programme> programmes = programmeService.get(code, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmes);
        return ResponseEntity.ok().headers(headers).body(programmes.getContent());
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping(path = "/encours")
    public ResponseEntity<List<Programme>> getProgrammeENCOURS(Pageable pageable) {
        log.debug("Consultation des Programmes en cours :");
        Page<Programme> programmes = programmeService.getENCOURS(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmes);
        return ResponseEntity.ok().headers(headers).body(programmes.getContent());
    }

    /**
     *
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Programme>> findAllProgrammes(Pageable pageable) {
        Page<Programme> programmes = programmeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), programmes);
        return ResponseEntity.ok().headers(headers).body(programmes.getContent());
    }

    /**
     * Access granted to ADMIN
     *
     * @param id : id of Programme to be deleted
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyAuthority(\"" + AppUtil.ADMIN + "\")")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("Suppression du Programme : {}", id);
        programmeService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
                .build();
    }
}
